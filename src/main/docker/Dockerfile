# 该镜像需要依赖的基础镜像
FROM eclipse-temurin:17-jdk-alpine
# 指定维护者的名字
MAINTAINER lixundu
# docker命令执行目录
WORKDIR /website
#用来定义容器运行时可以挂在到宿主机的目录
#VOLUME   /website
# 将当前目录下的jar包复制到docker容器的/website目录下
ADD app.jar /website/app.jar
# 将当前目录下的jconf文件夹复制到docker容器的/website目录下
ADD conf   /website/conf
# 声明需要暴露的端口
EXPOSE 6060
# 指定docker容器启动时运行jar包
ENTRYPOINT ["java","-Xms256m","-Xmx256m", "-jar","/website/app.jar","--spring.profiles.active=prd","--spring.config.location=/website/conf/application.yml"]
