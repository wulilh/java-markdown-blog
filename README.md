# java-markdown-blog
> 用Git来做备份，感觉很可靠，就用Markdown写了一些笔记备份到github，平常更新也都是本地改完直接提交，用一些比较成熟的博客系统，需要人力以及服务器资源，像我这种懒人隔段时间估计就不回去碰服务器了，就想着写个这玩意玩玩
> 

一个粗粗糙糙的用Java写的Markdown博客，用来将本地Markdown文档发布到网站上。
> 前端参考：https://github.com/xiongsihao/myblog
>
>后端：Springboot + Thymeleaf

![](https://raw.githubusercontent.com/wulilinghan/PicBed/main/img2023/202302052330187.png)

# IDEA集成Docker并部署
## docker配置文件修改
```shell
# 编辑docker.service
vi /lib/systemd/system/docker.service
 
#
ExecStart=/usr/bin/dockerd -H fd:// --containerd=/run/containerd/containerd.sock
#新增 -H tcp://0.0.0.0:2375 -H unix:///var/run/docker.sock ,保存退出
ExecStart=/usr/bin/dockerd -H fd:// --containerd=/run/containerd/containerd.sock -H tcp://0.0.0.0:2375 -H unix:///var/run/docker.sock
 
#更新配置文件
systemctl daemon-reload
#重启docker服务
systemctl restart docker.service
```
## idea配置
tcp://192.168.3.50:2375
![idea-docker](https://raw.githubusercontent.com/wulilinghan/PicBed/main/img2023/202302051749751.png)

# 说明
- 通过文档相对路径确定文档唯一id
