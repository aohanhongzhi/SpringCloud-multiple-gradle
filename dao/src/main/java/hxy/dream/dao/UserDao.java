package hxy.dream.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import hxy.dream.dao.mapper.UserMapper;
import hxy.dream.dao.model.UserModel;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * @author iris
 */
@Component
public class UserDao {

    @Resource
    UserMapper userMapper;

    public List<UserModel> selectByName(String name) {
        QueryWrapper<UserModel> objectQueryWrapper = new QueryWrapper<UserModel>();
        objectQueryWrapper.eq("name", name);
        return userMapper.selectList(objectQueryWrapper);
    }
}
