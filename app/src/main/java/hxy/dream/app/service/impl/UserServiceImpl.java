package hxy.dream.app.service.impl;

import hxy.dream.app.entity.param.UserParam;
import hxy.dream.app.service.UserService;
import hxy.dream.dao.mapper.UserMapper;
import hxy.dream.dao.modle.UserModel;
import hxy.dream.entity.enums.GenderEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;

    @Override
    public UserModel add(UserParam userParam) {
        UserModel userModel = new UserModel();
        userModel.setName(userParam.getName());
        userModel.setGender(GenderEnum.BOY);
        int insert = userMapper.insert(userModel);
        log.debug("\n====>插入影响行数：{}",insert);
        return userModel;
    }

    @Override
    public UserModel get(String id) {
        return null;
    }
}
