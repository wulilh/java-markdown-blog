# 该镜像需要依赖的基础镜像
#FROM openjdk:8u322-jdk
#FROM openjdk:17
FROM eclipse-temurin:17-jdk-alpine
# 指定维护者的名字
MAINTAINER lixundu<1902325071@qq.com>
# 设置匿名卷
VOLUME /data
# 进入容器内的默认目录
WORKDIR /website
# 复制指定目录或文件到docker容器的/website目录下(原目录或文件没层级就是当前目录)
ADD app.jar /website/app.jar
ADD conf   /website/conf
# 声明需要暴露的端口
EXPOSE 6060
# 指定docker容器启动时运行jar包
ENTRYPOINT ["java","-Xms256m","-Xmx256m", "-jar","/website/app.jar","--spring.profiles.active=prd","--spring.config.location=/website/conf/application.yml"]
