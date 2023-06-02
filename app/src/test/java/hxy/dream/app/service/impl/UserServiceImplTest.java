package hxy.dream.app.service.impl;

import hxy.dream.BaseTest;
import hxy.dream.app.entity.param.UserParam;
import hxy.dream.app.service.UserService;
import hxy.dream.dao.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class UserServiceImplTest  extends BaseTest {

    @Autowired
    UserService userService;

    @Test
    @Order(1)
    public void arunTest(){
        UserParam userParam = new UserParam();
        userParam.setName("111");
        UserModel userModel = userService.add(userParam);
        log.info("\n====>{}",userModel);
    }

    @Test
    public void bGetTest(){

        UserModel userModel = userService.get("87");
        log.info("\n====>{}",userModel);
    }

    @Test
    public void listTest(){

        List<UserModel> userModels = userService.list();
        log.info("\n====>{}",userModels);
    }
}
