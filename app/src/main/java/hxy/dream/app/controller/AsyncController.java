package hxy.dream.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;
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
     * 从这个接口可以更好的理解异步模型的一种实现手段就是多线程！
     * <p>
     * 来自一本书《Java工程师修炼之道》
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
}
