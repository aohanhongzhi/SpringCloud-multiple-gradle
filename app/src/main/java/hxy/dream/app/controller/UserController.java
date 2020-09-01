package hxy.dream.app.controller;

import hxy.dream.app.entity.param.UserParam;
import hxy.dream.app.service.UserService;
import hxy.dream.dao.modle.UserModel;
import hxy.dream.entity.vo.BaseResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author eric
 */
@Slf4j
@Validated
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("get/{id}")
    public BaseResponseVO get(@PathVariable("id") String id) {
        log.info("\n====>当前添加的id是{}", id);
        UserModel userModel = userService.get(id);
        return BaseResponseVO.success(userModel);
    }

    @GetMapping("list")
    public BaseResponseVO list() {
        List<UserModel> userModels = userService.list();
        return BaseResponseVO.success(userModels);
    }

    @PostMapping("add/")
    public BaseResponseVO save(@Valid @RequestBody UserParam userParam) {
        log.debug("\n====>当前添加的用户信息是{}", userParam);
        UserModel userModel = userService.add(userParam);
        return BaseResponseVO.success(userModel);
    }

    @PostMapping("add2")
    public BaseResponseVO save2(@Valid UserParam userParam) {
        log.debug("\n====>当前添加的用户信息是{}", userParam);
        UserModel userModel = userService.add(userParam);
        return BaseResponseVO.success(userModel);
    }
}
