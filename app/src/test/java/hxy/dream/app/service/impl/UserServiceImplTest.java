package hxy.dream.app.service.impl;

import hxy.dream.BaseTest;
import hxy.dream.app.entity.param.UserParam;
import hxy.dream.app.service.UserService;
import hxy.dream.dao.modle.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class UserServiceImplTest  extends BaseTest {
    @Autowired
    UserService userService;
    @Test
    public void runTest(){
        UserParam userParam = new UserParam();
        userParam.setName("111");
        UserModel userModel = userService.add(userParam);
        log.info("\n====>{}",userModel);
    }
}
