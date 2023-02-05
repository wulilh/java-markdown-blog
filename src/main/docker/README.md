
# docker 打包
将docker文件夹上传到服务器，按照当前目录结构

> - conf 放置spring配置文件
> 
> - sqlite/jmb.db是初始化的db文件


进入到 Dockerfile 文件目录，执行
```shell
# 打到本地docker仓库里
docker build -t java-markdown-blog:0.0.1-SNAPSHOT .

```
> 
> java-markdown-blog:0.0.1-SNAPSHOT 镜像名:版本号
> 
> .  代表指定当前目录的Dockerfile构建

![](https://raw.githubusercontent.com/wulilinghan/PicBed/main/img2023/202302042344365.png)

# 运行
```shell
# 创建挂在目录以及db文件
mkdir -p /opt/docker/java-markdwon-blog/website/{index,markdown-articles,log,conf,sqlite} \
&& touch /opt/docker/java-markdwon-blog/website/sqlite/jmb.db 

docker run -d --name java-markdown-blog --restart=always \
-p 8888:6060 \
-v /opt/docker/java-markdwon-blog/website/conf:/website/conf \
-v /opt/docker/java-markdwon-blog/website/index:/website/index \
-v /opt/docker/java-markdwon-blog/website/markdown-articles:/website/markdown-articles \
-v /opt/docker/java-markdwon-blog/website/log:/website/log \
-v /opt/docker/java-markdwon-blog/website/sqlite:/website/sqlite \
java-markdown-blog:0.0.1-SNAPSHOT
```