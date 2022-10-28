# easyChat
基于java servlet 和 WebSocket 的简易的网上聊天室。

## 项目构建
本项目使用Maven工具构建。

## 项目运行
### Java版本

Java版本为18（不是最低版本），因为在`pom.xml`中配置了编译版本，需要用其他`java`版本，请修改`pom.xml`文件。
```
<properties>
  <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  <maven.compiler.source>you're java version</maven.compiler.source>
  <maven.compiler.target>you're java version</maven.compiler.target>
</properties>
```

### Tomcat和Maven
需要用到[Tomcat](https://tomcat.apache.org/), 版本为9.0.65。

**注意** 因为Java EE 向 Eclipse基金会的转移，Java EE 所有实现的 API 的主要包已经从 javax.* 改为 jakarta.* 。如果需要使用[Tomcat 10](https://tomcat.apache.org/download-10.cgi),请按照官方提供的工具进行迁移。

运行之前请保证IDEA中配置好Maven和Tomcat。

### Redis
还需要Redis数据库。操作Redis使用[jedis](https://github.com/redis/jedis) 。

Pom.xml 中引入方式如下：
```
  <!-- https://mvnrepository.com/artifact/redis.clients/jedis -->
  <dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
    <version>4.2.3</version>
  </dependency>
```

本代码中使用JedisPool,其配置文件在`src/main/resources/`目录下。如需修改redis服务器地址，端口号和最大连接数等等参数，请修改`jedisConfig.properties`文件。
```
jedis.host = you're redis client host
jedis.port = you're redis client port
#最大连接数
jedis.maxTotal = 30
#最大空闲连接数
jedis.maxIdle = 10
```



## 参考资料
- [《左手MongoDB，右手Redis——从入门到商业实战》书籍配套源代码](https://github.com/kingname/SourceCodeofMongoRedis)
- [WebSocket打造在线聊天室](https://www.bilibili.com/video/BV1r54y1D72U/?p=3&share_source=copy_web&vd_source=26c5644968a6b6b45bdd07e9fa81bbb9)
