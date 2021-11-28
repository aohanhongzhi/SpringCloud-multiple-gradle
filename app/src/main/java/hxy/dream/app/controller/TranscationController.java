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

    @RequestMapping("/")
    public BaseResponseVO test() {
        return transactionService.experiment();
    }


}
