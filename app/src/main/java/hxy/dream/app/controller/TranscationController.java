package hxy.dream.app.controller;

import hxy.dream.app.service.TransactionService;
import hxy.dream.entity.vo.BaseResponseVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author eric
 * @program multi-gradle
 * @description 事务测试
 * @date 2021/11/28
 */
@RestController
@RequestMapping("/transaction")
public class TranscationController {

    @Resource
    TransactionService transactionService;

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

        new Thread(() -> {
            transactionService.isolation();
        }).start();
        new Thread(() -> {
            transactionService.isolation1();
        }).start();

        return BaseResponseVO.success();
    }

}
