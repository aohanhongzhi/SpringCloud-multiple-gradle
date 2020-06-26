package hxy.dream.app.service;

import hxy.dream.app.entity.param.UserParam;
import hxy.dream.dao.modle.UserModel;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserModel add(UserParam userParam);

    UserModel get(String id);
}
