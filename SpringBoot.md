SpringBoot
===

[面试官：SpringBoot如何实现缓存预热？](https://mp.weixin.qq.com/s/6h6IGjBp3hYduXIcE-coYA)

# SpringBoot AutoConfig

SpringBoot的 自动装配看这个类就可以了 ConfigurationClassPostProcessor

### SpringBoot 启动而执行。

1. 使用ApplicationListener启动监听事件实现缓存预热。
2. 使用 @PostConstruct 注解实现缓存预热。
3. 使用 CommandLineRunner 或 ApplicationRunner 实现缓存预热。
4. 通过实现 InitializingBean 接口，并重写 afterPropertiesSet 方法实现缓存预热。