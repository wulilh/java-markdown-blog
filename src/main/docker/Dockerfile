# 该镜像需要依赖的基础镜像
#FROM openjdk:8u322-jdk
#FROM openjdk:17
#FROM eclipse-temurin:17-jdk-alpine
FROM openjdk:17-jdk-slim
# 指定维护者的名字
MAINTAINER wulililinghan 1902325071@qq.com
# 设定时区
ENV TZ=Asia/Shanghai
# 设置匿名卷
VOLUME /tmp
# 进入容器内的默认目录
WORKDIR /website
# 复制指定目录或文件到docker容器的website目录下
COPY app.jar /website/app.jar
COPY conf   /website/conf
# 导入启动脚本
COPY docker-entrypoint.sh /website/docker-entrypoint.sh
RUN chmod +x /website/docker-entrypoint.sh
# 声明需要暴露的端口
EXPOSE 6060
# 指定docker容器启动时运行jar包
ENTRYPOINT ["/website/docker-entrypoint.sh"]
