package io.github.tlh.jmb.controller;

import cn.hutool.core.util.IdUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import io.github.tlh.jmb.common.pojo.UserGithub;
import io.github.tlh.jmb.mapper.UserGithubMapper;
import io.github.tlh.jmb.service.IFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wuliling Created By 2023-01-30 16:18
 **/
@RestController
@RequestMapping("/upload")
@Slf4j
public class FileController {

    @Resource
    private IFile iFile;

    @Resource
    private UserGithubMapper userGithubMapper;

    @Value("${image.upload.base-url}")
    private String url;

    @PostMapping(value = "/uploadGithubImg")
    public Map<String, Object> uploadGithubImg(@RequestParam(value = "file") MultipartFile upfile, HttpSession session) throws IOException {
        Map<String, Object> map = new HashMap<>();
        String fileName = upfile.getOriginalFilename();
        File file = new File(url + fileName);
        try {
            upfile.transferTo(file);

            //根据用户id和UUID组合成文件名
            String nodeId = (String) session.getAttribute("node_id");
            UserGithub gUser = userGithubMapper.findByNodeId(nodeId);
            String uploadFileName = "GithubAvatar_id_" + gUser.getGithubId() + "_" + IdUtil.simpleUUID();
            log.info("上传的文件名为：" + uploadFileName);

            Map<String, Object> response = iFile.uploadFile(file, uploadFileName);
            String imageUrl = (String) response.get("imageUrl");
            //上传图片之后更新对应用户的头像链接并将session中的头像信息更新
            userGithubMapper.updateAvatar(imageUrl, gUser.getId());
            map.put("url", imageUrl);
            map.put("state", "SUCESS");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
