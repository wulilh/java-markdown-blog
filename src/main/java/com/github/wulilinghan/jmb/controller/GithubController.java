package com.github.wulilinghan.jmb.controller;

import cn.hutool.core.util.IdUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wulilinghan.jmb.common.config.GithubConfig;
import com.github.wulilinghan.jmb.common.pojo.UserGithub;
import com.github.wulilinghan.jmb.common.utils.HttpClientUtils;
import com.github.wulilinghan.jmb.mapper.UserGithubMapper;

import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

/**
 * @author wuliling Created By 2023-01-30 11:36
 **/
@Controller
public class GithubController {

    @Resource
    private UserGithubMapper userGithubMapper;
    @Resource
    private GithubConfig githubConfig;

    @Value("${website.cookie.time}")
    private Integer time;


    @GetMapping("/github/oauth")
    public String github(HttpSession session) {
        //随机uuid,用于第三方应用防止CSRF攻击
        String uuid = IdUtil.simpleUUID();
        session.setAttribute("state", uuid);

        //Step1：获取Authorization Code
        String url = "https://github.com/login/oauth/authorize?" +
                "client_id=" + githubConfig.getClientId() +
                "&redirect_uri=" + URLEncoder.encode(githubConfig.getCallback()) +
                "&state=" + uuid;
        return "redirect:" + url;
    }

    /**
     * 回调地址
     */
    @RequestMapping("/github/callback")
    public String callback(String code, String state, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!StringUtils.isEmpty(code) && !StringUtils.isEmpty(state)) {
            HttpSession session = request.getSession();
            //解决关闭浏览器就退出登录的bug
            Cookie cookie = new Cookie("JSESSIONID", session.getId());
            cookie.setPath(request.getContextPath() + "/");
            //设置cookie的生命周期为十天，单位秒。 60*60*24*10
            cookie.setMaxAge(time);
            response.addCookie(cookie);
            //拿到我们的code,去请求token
            //发送一个请求到
            String tokenUrl = String.format(githubConfig.getTokenUrl(), code);
            //得到的responseStr是一个字符串需要将它解析放到map中
            String responseStr = HttpClientUtils.doGet(tokenUrl);
            // 调用方法从map中获得返回的--》 令牌
            String token = HttpClientUtils.getMap(responseStr).get("access_token");

            //根据token发送请求获取登录人的信息  ，通过令牌去获得用户信息
            String userInfoUrl = String.format(githubConfig.getUserInfoUrl(), token);
            //json
            responseStr = HttpClientUtils.doGet(userInfoUrl);
            Map<String, String> responseMap = HttpClientUtils.getMapByJson(responseStr);
            //node_id,用来唯一标识用户
            session.setAttribute("node_id", responseMap.get("node_id"));
            //github名
            session.setAttribute("nickname", responseMap.get("login"));
            /*github回调的头像url地址大概率加载不出(加载慢)，不使用github账号头像图片*/
            //session.setAttribute("avatar",responseMap.get("avatar_url")); //github头像URL

            //登录状态
            session.setAttribute("loginStatus", "true");
            //登录方式,1 QQ，2 github
            session.setAttribute("loginType", 2);

            UserGithub githubUserInfo = userGithubMapper.findByNodeId(responseMap.get("node_id"));
            //无此node_id记录，创建新github用户记录
            if (githubUserInfo == null) {
                int Min = 1;
                int Max = 20;
                //随机生成一个1到20的数字
                int result = Min + (int) (Math.random() * ((Max - Min) + 1));
                //图片从CDN服务器上的20张头像库里面随机取
                String avatarUrl = "http://cdn.xiongsihao.com/GithubAvatar" + result + ".png";
                UserGithub github = new UserGithub();
                github.setAvatar(avatarUrl);
                github.setCreatedTime(responseMap.get("created_at"));
                github.setNickname(responseMap.get("login"));
                github.setIndexUrl(responseMap.get("html_url"));
                github.setNodeId(responseMap.get("node_id"));
                github.setPublicRepos(responseMap.get("public_repos"));
                github.setNickname(responseMap.get("login"));
                github.setSubscriptions(responseMap.get("subscriptions_url"));
                github.setUpdatedTime(responseMap.get("updated_at"));
                github.setGithubId(responseMap.get("id"));
                github.setReceivedEventsUrl(responseMap.get("received_events_url"));
                github.setLoginTime(new Date().toString());

                userGithubMapper.insert(github);
            }
            // 成功则登陆，跳转资料页面
            return "redirect:/githubInfo";
        }
        // 失败返回到首页
        return "redirect:/index";
    }

    @GetMapping("/githubInfo")
    public String post(HttpSession session, Model model) throws InterruptedException {
        String nodeId = (String) session.getAttribute("node_id");
        UserGithub user = userGithubMapper.findByNodeId(nodeId);
        //更新session中头像信息
        session.setAttribute("avatar", user.getAvatar());
        model.addAttribute("github", user);
        return "githubInfo";
    }

    @PostMapping("/saveIp")
    public String post(HttpSession session, UserGithub github) {
        String nodeId = (String) session.getAttribute("node_id");
        UserGithub githubuser = userGithubMapper.findByNodeId(nodeId);
        if (githubuser.getCip() == null) {
            //ip信息为空，第一次登录，将前端传入的ip信息存入
            userGithubMapper.addIpAddress(github.getCip(), github.getCid(), github.getCname(), nodeId);
        }
        return "githubInfo";
    }

    @GetMapping("/githublogout")
    public String logout(HttpSession session) {
        //退出登录，将session中的user信息清除
        session.removeAttribute("loginStatus");
        session.removeAttribute("avatar");
        session.removeAttribute("nickname");
        session.removeAttribute("node_id");
        session.removeAttribute("loginType");
        return "redirect:/index";
    }
}
