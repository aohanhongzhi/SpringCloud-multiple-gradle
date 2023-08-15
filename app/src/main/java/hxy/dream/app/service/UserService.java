package hxy.dream.app.service;

import hxy.dream.app.entity.param.UserParam;
import hxy.dream.dao.model.UserModel;
import hxy.dream.entity.vo.BaseResponseVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserModel add(UserParam userParam);

    UserModel get(String id);

    BaseResponseVO exist(String id);

    List<UserModel> list();

    BaseResponseVO delete(Integer id);
}
