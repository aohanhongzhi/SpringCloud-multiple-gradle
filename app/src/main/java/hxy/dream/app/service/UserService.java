package hxy.dream.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import hxy.dream.app.entity.param.UserParam;
import hxy.dream.dao.model.UserModel;
import hxy.dream.entity.vo.BaseResponseVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends IService<UserModel> {
    UserModel add(UserParam userParam);

    UserModel get(String id);

    BaseResponseVO exist(String id);

    List<UserModel> list();
}
