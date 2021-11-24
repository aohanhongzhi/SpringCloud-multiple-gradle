package hxy.dream.app.controller;

import hxy.dream.entity.vo.BaseResponseVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author eric
 * @program eric-dream
 * @description
 * @date 2021/8/29
 */
@RestController
public class SystemController {
    @GetMapping("/")
    public BaseResponseVO index(){
        return BaseResponseVO.success("SpringBoot");
    }
}
