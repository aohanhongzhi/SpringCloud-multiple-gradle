package hxy.dream.app.controller;

import hxy.dream.entity.vo.BaseResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

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
    public BaseResponseVO index() {

//        try {
//            Thread.sleep(10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        try {
            TimeUnit.SECONDS.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("{}", Thread.currentThread().getName());

        return BaseResponseVO.success("SpringBoot项目工程");
    }

    @GetMapping("encoding")
    public String encoding() {
        return "encoding text/plain, 中文应该是乱码的。 Content-Type:text/html;charset=UTF-8就不会乱码 ";
    }

}
