package hxy.dream.app.service;

import hxy.dream.dao.modle.UserModel;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserModel add(String id);

    UserModel get(String id);
}
