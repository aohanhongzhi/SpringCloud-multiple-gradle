package hxy.dream.app.controller;

import hxy.dream.app.service.TransactionService;
import hxy.dream.entity.vo.BaseResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author eric
 * @program multi-gradle
 * @description 事务测试
 * @date 2021/11/28
 */
@RestController
@RequestMapping("/transaction")
public class TranscationController {

    private static final Logger log = LoggerFactory.getLogger(TranscationController.class);


    @Resource
    TransactionService transactionService;

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors() * 2, 200, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(5), new ThreadPoolExecutor.CallerRunsPolicy());

    /**
     * 事务传播机制的研究
     *
     * @return
     */
    @RequestMapping("/propagation")
    public BaseResponseVO test() {
        return transactionService.propagation();
    }


    /**
     * 事务隔离机制的研究。
     * 本方法主要研究，当数据库的隔离级别与Spring设置的隔离级别不一致的时候，到底以谁为准。实际测试结果是以Spring为准。
     *
     * @return
     */
    @RequestMapping("/isolation")
    public BaseResponseVO isolation() {
        executor.execute(() -> {
            log.info("\n====>当前线程名字" + Thread.currentThread().getName());
            transactionService.isolation();
        });
        executor.execute(() -> {
            log.info("\n====>当前线程名字" + Thread.currentThread().getName());
            transactionService.isolation1();
        });

        // 主动关闭线程池
        executor.shutdown();

//        new Thread(() -> {
//            transactionService.isolation();
//        }).start();
//        new Thread(() -> {
//            transactionService.isolation1();
//        }).start();

        return BaseResponseVO.success();
    }

}
