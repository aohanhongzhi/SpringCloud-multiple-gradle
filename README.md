Eric-Dream
===
本项目是基于Gradle构建的多模块SpringCloud工程。采用`传统线程模型`的SpringWeb框架，mybatis-plus和mysql官方驱动。具体框架如下表：

技术 | 说明 | 官网
----|----|----
Spring Boot | 容器+MVC框架 | [https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot)
Gradle | 项目构建工具 | [https://gradle.com/](https://gradle.com/)
Spring Security | 认证和授权框架 | [https://spring.io/projects/spring-security](https://spring.io/projects/spring-security)
MyBatis | ORM框架  | [http://www.mybatis.org/mybatis-3/zh/index.html](http://www.mybatis.org/mybatis-3/zh/index.html)
MyBatisPlus |ORM框架补充 | [https://mybatis.plus/](https://mybatis.plus/)
MyBatisGenerator | 数据层代码生成 | [http://www.mybatis.org/generator/index.html](http://www.mybatis.org/generator/index.html)
PageHelper | MyBatis物理分页插件 | [https://pagehelper.github.io/](https://pagehelper.github.io/)
Swagger-UI | 文档生产工具 | [https://github.com/swagger-api/swagger-ui](https://github.com/swagger-api/swagger-ui)
Hibernator-Validator | 验证框架 | [http://hibernate.org/validator/](http://hibernate.org/validator/)
Elasticsearch | 搜索引擎 | [https://github.com/elastic/elasticsearch](https://github.com/elastic/elasticsearch)
RabbitMq | 消息队列 | [https://www.rabbitmq.com/](https://www.rabbitmq.com/)
Redis | 分布式缓存 | [https://redis.io/](https://redis.io/)
MongoDb | NoSql数据库 | [https://www.mongodb.com/](https://www.mongodb.com/)
Docker | 应用容器引擎 | [https://www.docker.com/](https://www.docker.com/)
Hikari | SpringBoot默认数据库连接池 | [https://github.com/brettwooldridge/HikariCP](https://github.com/brettwooldridge/HikariCP)
Druid | 数据库连接池 | [https://github.com/alibaba/druid](https://github.com/alibaba/druid)
JWT | JWT登录支持 | [https://github.com/jwtk/jjwt](https://github.com/jwtk/jjwt)
LogStash | 日志收集 | [https://github.com/logstash/logstash-logback-encoder](https://github.com/logstash/logstash-logback-encoder)
Lombok | 简化对象封装工具 | [https://github.com/rzwitserloot/lombok](https://github.com/rzwitserloot/lombok)
loc |代码行数统计 | https://github.com/cgag/loc


### 命令打包，跳过TEST
```shell script
./gradlew bootJar -x test
```

### 多模块构建，依赖关系解决
```groovy
//    implementation的依赖是不可以传递的而，entity需要被app依赖，所以需要加上
//    implementation project(':entity') /* 子模块之间的依赖 */
    compile project(':entity') /* 子模块之间的依赖 */
```

### 版本指定，类似dependencyManager

### docker自动化跑起来
google出品的一个插件，可以直接将SpringBoot构建推送到Docker仓库
```groovy
    id "com.google.cloud.tools.jib" version "2.0.0"
```


### 统一序列化枚举

#### Controller层
参考腾讯文档：https://docs.qq.com/doc/DSFpuQkRrdk9xUlF6

![](./asset/img/enums.png)

#### ORM层
> 参考 mybatis-plus:https://mp.baomidou.com/guide/enum.html