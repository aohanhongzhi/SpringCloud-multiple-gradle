
### 执行初始化的sql语句

有两种方案。一种借助与mybatis-plus 另一种就是mybatis的

#### 基于mybatis的sql执行方案

[hxy.dream.common.init.ApplicationStartupRunner](common/src/main/java/hxy/dream/common/init/ApplicationStartupRunner.java)

#### 基于mybatis-plus的sql执行方案

##### 基于mybatis-plus配置直接执行

https://www.baomidou.com/pages/226c21/#%E9%85%8D%E7%BD%AE

```yaml
# DataSource Config
spring:
  datasource:
    driver-class-name: org.h2.Driver
    username: root
    password: test
  sql:
    init:
      schema-locations: classpath:db/schema-h2.sql
      data-locations: classpath:db/data-h2.sql
```

##### 基于mybatis-plus的sql执行方案

[hxy.dream.common.init.MysqlDdl](common/src/main/java/hxy/dream/common/init/MysqlDdl.java)

### OpenFeign要退出历史舞台了

推荐 RestTemplate或者WebClient

WebClient 声明式的API调用：
[RemoteApiConfig](common/src/main/java/hxy/dream/common/configuration/RemoteApiConfig.java)