package hxy.dream.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author eric
 * @program multi-gradle
 * @description 异步接口
 * @date 2022/1/23
 */
@RestController
@RequestMapping("/async")
public class AsyncController {
    private static final Logger log = LoggerFactory.getLogger(AsyncController.class);

    /**
     * 注意这个线程池的最大线程数队列长度
     */
    @Autowired
    ThreadPoolTaskExecutor applicationTaskExecutor;

    /**
     * 从这个接口可以更好的理解异步模型的一种实现手段就是多线程！
     * <p>
     * 来自一本书《Java工程师修炼之道》
     * <p>
     * 其实这里的描述还是差点意思，这里只能算作是SpringMVC级别的业务异步，因为真正的Servlet3级别的IO异步，是把请求和返回的上下文传到一个新的线程里，返回客户端的动作放在了新线程了。这样就把业务线程和IO线程分开了。
     *
     * @return
     */
    @RequestMapping("/test")
    public Callable<String> callable() {
        log.info("当前AsyncController线程{}", Thread.currentThread().getName());
        return new Callable<String>() {

            @Override
            public String call() throws Exception {
                TimeUnit.SECONDS.sleep(3);
                log.info("当前Callable线程{}", Thread.currentThread().getName());
                return "Async Controller Result";
            }
        };
    }

    @RequestMapping("concurrency")
    public Object concurrency() {
        log.info("请求开始执行，是否为守护线程={}", Thread.currentThread().isDaemon());

        long start = System.currentTimeMillis();

        HashMap<String, Object> map = new HashMap();

//        CountDownLatch countDownLatch = new CountDownLatch();

        // 异步执行
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                map.put("线程1", System.currentTimeMillis());
                // 有非守护线程，那么守护线程就不会退出。
                log.info("{}是否为守护线程={}", Thread.currentThread().getName(), Thread.currentThread().isDaemon());

            } catch (InterruptedException e) {
                log.error("{}", e.getMessage(), e);
            }
        }, applicationTaskExecutor);


        // 异步执行
        CompletableFuture<Void> voidCompletableFuture1 = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                map.put("线程2", System.currentTimeMillis());
                log.info("{}是否为守护线程={}", Thread.currentThread().getName(), Thread.currentThread().isDaemon());

            } catch (InterruptedException e) {
                log.error("{}", e.getMessage(), e);
            }
        }, applicationTaskExecutor);

        try {
            // 等待上面返回结果
            CompletableFuture.allOf(voidCompletableFuture1, voidCompletableFuture).get();
        } catch (InterruptedException e) {
            log.error("{}", e.getMessage(), e);
        } catch (ExecutionException e) {
            log.error("{}", e.getMessage(), e);
        }

        long end = System.currentTimeMillis();
        long totalTime = (end - start) / 1000;
        map.put("消耗时间", totalTime);
        log.info("请求处理完成{}", totalTime);

        return map;
    }
}
