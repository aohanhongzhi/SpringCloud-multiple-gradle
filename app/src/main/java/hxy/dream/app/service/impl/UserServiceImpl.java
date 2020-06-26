package hxy.dream.app.service.impl;

import hxy.dream.app.service.UserService;
import hxy.dream.dao.modle.UserModel;
import hxy.dream.entity.enums.GenderEnum;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserModel add(String id) {
        UserModel userModel = new UserModel();
        userModel.setId(id);
        userModel.setGender(GenderEnum.BOY);
        return userModel;
    }

    @Override
    public UserModel get(String id) {
        return null;
    }
}
