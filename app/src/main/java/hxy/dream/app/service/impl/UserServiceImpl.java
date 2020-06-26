package hxy.dream.app.service.impl;

import hxy.dream.app.entity.param.UserParam;
import hxy.dream.app.service.UserService;
import hxy.dream.dao.modle.UserModel;
import hxy.dream.entity.enums.GenderEnum;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserModel add(UserParam userParam) {
        UserModel userModel = new UserModel();
        userModel.setName(userParam.getName());
        userModel.setGender(GenderEnum.BOY);
        return userModel;
    }

    @Override
    public UserModel get(String id) {
        return null;
    }
}
