SpringBoot AutoConfig
===
SpringBoot的 自动装配看这个类就可以了 ConfigurationClassPostProcessor



# 特定环境配置生效

Springboot中的@Profile注解

https://blog.csdn.net/loongkingwhat/article/details/105745303

对于`@PostConstruct`注解而言，`@Profile`注解不生效

程序里可以进一步控制

org.springframework.context.EnvironmentAware

```java
    @PostConstruct
    public void init() throws SchedulerException, TaskException {
        String activeProfile = EnvironmentUtils.getActiveProfile();
        if ("prod".equals(activeProfile) || "beta".equals(activeProfile)) {
          // 特殊环境初始化
        }
    }
```