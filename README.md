Eric-Dream
===


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