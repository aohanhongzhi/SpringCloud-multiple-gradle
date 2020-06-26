package hxy.dream.app.controller;

import hxy.dream.app.entity.param.UserParam;
import hxy.dream.app.service.UserService;
import hxy.dream.dao.modle.UserModel;
import hxy.dream.entity.vo.BaseResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("get/{id}")
    public BaseResponseVO add(@PathVariable("id") String id) {
        log.info("\n====>当前添加的id是{}", id);
        UserModel userModel = userService.get(id);
        return BaseResponseVO.success(userModel);
    }

    @PostMapping("add/{id}")
    public BaseResponseVO save(UserParam userParam) {
        log.info("\n====>当前添加的id是{}", id);
        UserModel userModel = userService.add(id);
        return BaseResponseVO.success(userModel);
    }
}
