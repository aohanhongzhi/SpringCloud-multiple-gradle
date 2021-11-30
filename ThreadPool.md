线程池研究
===

1. [Java 线程池会自动关闭吗？](https://blog.csdn.net/weixin_43207056/article/details/103438809)
2. [面试问我，创建多少个线程合适？我该怎么说](https://docs.qq.com/doc/DSEJ2VGl4S0dodmtK)
3. [两个活动的并发控制](https://aohanhongzhi.gitee.io/study/#/src/%E5%B9%B6%E5%8F%91/%E7%BA%BF%E7%A8%8B%E4%BC%98%E5%8C%96)
```java
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
        new LinkedBlockingQueue<Runnable>(5), new ThreadPoolExecutor.CallerRunsPolicy());


  //  存在返回值 Callable
    Future<?> submit = executor.submit(() -> {
        return 1;
    });

    try {
        // 获取Callable的返回值
        Object o = submit.get();
        log.info("\n====>{}",o);
    } catch (InterruptedException e) {
        e.printStackTrace();
    } catch (ExecutionException e) {
        e.printStackTrace();
    }
```
  