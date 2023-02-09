
> 我这里服务器是centos7，已安装docker

# 方式一 手动 docker 打包
将docker文件夹上传到服务器，按照当前目录结构

> - conf 放置spring配置文件

进入到 Dockerfile 文件目录，执行
```shell
# 打到本地docker仓库里
docker build -t java-markdown-blog:0.0.1-SNAPSHOT .

```
> 
> java-markdown-blog:0.0.1-SNAPSHOT 镜像名:版本号
> 
> .  代表指定当前目录的 Dockerfile 文件构建

![](https://raw.githubusercontent.com/wulilinghan/PicBed/main/img2023/202302042344365.png)

## 运行镜像
```shell
# 创建挂在目录以及db文件
mkdir -p /opt/docker/java-markdwon-blog/website/{index,markdown-articles,log,conf,sqlite} \
&& touch /opt/docker/java-markdwon-blog/website/sqlite/jmb.db 

docker run -d --name java-markdown-blog --restart=always \
-p 6060:6060 \
-v /opt/docker/java-markdwon-blog/website/conf:/website/conf \
-v /opt/docker/java-markdwon-blog/website/index:/website/index \
-v /opt/docker/java-markdwon-blog/website/markdown-articles:/website/markdown-articles \
-v /opt/docker/java-markdwon-blog/website/log:/website/log \
-v /opt/docker/java-markdwon-blog/website/sqlite:/website/sqlite \
java-markdown-blog:0.0.1-SNAPSHOT
```

# 方式二 docker-maven 插件
首先还是需要 Dockerfile 文件 ，其次maven pom文件新增以下内容，maven有执行package动作会进行打包
```xml
<build>

    ....

    <plugins>

        ......
        
        <!--使用docker-maven-plugin插件，官网：https://github.com/spotify/docker‐maven‐plugin-->
        <plugin>
            <groupId>com.spotify</groupId>
            <artifactId>docker-maven-plugin</artifactId>
            <version>1.2.0</version>
            <!--将插件绑定在某个phase执行-->
            <executions>
                <execution>
                    <id>build-image</id>
                    <!--将插件绑定在package这个phase上。也就是说，用户只需执行mvn package，就会自动执行mvn docker:build-->
                    <phase>package</phase>
                    <goals>
                        <goal>build</goal>
                    </goals>
                </execution>
            </executions>

            <configuration>
                <!--指定生成的镜像名-->
                <imageName>${project.artifactId}</imageName>
                <!--指定标签，可添加多个imageTag，为同一个镜像指定多个标签-->
                <imageTags>
                    <imageTag>latest</imageTag>
                    <imageTag>${project.version}</imageTag>
                </imageTags>

                <!-- 指定 Dockerfile 路径 这里会将 dockerDirectory下的文件拷贝到/target/docker 目录下-->
                <dockerDirectory>${project.basedir}/src/main/docker</dockerDirectory>

                <!-- 这里是复制 jar 包到 docker 容器指定目录配置 -->
                <!-- 注意 这里是以target目录为根目录 -->
                <resources>
                    <resource>
                        <!-- 文件最终复制到 target/docker/ 目录下 -->
                        <targetPath>/</targetPath>
                        <!--指定需要复制项目jar包的所在路径，${project.build.directory}表示target目录 -->
                        <directory>${project.build.directory}</directory>
                        <!-- 用于指定需要复制的文件。${project.build.finalName}.jar指的是打包后的jar包文件　-->
                        <include>${project.build.finalName}.jar</include>
                    </resource>
                    <resource>
                        <!-- 文件最终复制到 target/docker/conf 目录下 -->
                        <targetPath>/conf</targetPath>
                        <directory>${project.build.outputDirectory}</directory>
                        <includes>
                            <include>*.yml</include>
                            <include>*.properties</include>
                        </includes>
                    </resource>
                </resources>

                <!--指定远程 docker api地址- 需要docker开启了远程访问 -->
                <dockerHost>http://192.168.3.50:2375</dockerHost>
                <!-- 如需重复构建相同标签名称的镜像，可将forceTags设为true，这样就会覆盖构建相同标签的镜像。 -->
                <forceTags>true</forceTags>
            </configuration>
        </plugin>
    </plugins>

    <!-- 打包文件名带时间戳 -->
    <!-- <finalName>${project.artifactId}_${build.time}</finalName>-->
    <finalName>app</finalName>
</build>
```

maven执行完后，重新运行启动镜像的命令
![](https://raw.githubusercontent.com/wulilinghan/PicBed/main/img2023/202302051947892.png)