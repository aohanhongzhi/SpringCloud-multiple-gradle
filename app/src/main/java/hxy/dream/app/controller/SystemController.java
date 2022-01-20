package hxy.dream.app.controller;

import hxy.dream.entity.vo.BaseResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author eric
 * @program eric-dream
 * @description
 * @date 2021/8/29
 */
@Slf4j
@RestController
public class SystemController {
    @GetMapping("/")
    public BaseResponseVO index(){

//        try {
//            Thread.sleep(10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        log.info("{}",Thread.currentThread().getName());

        return BaseResponseVO.success("SpringBoot");
    }
}
