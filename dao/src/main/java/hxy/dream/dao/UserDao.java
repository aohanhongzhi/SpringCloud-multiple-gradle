package hxy.dream.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import hxy.dream.dao.mapper.UserMapper;
import hxy.dream.dao.model.UserModel;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author iris
 * 没有必要添加DAO层
 */
@Deprecated
@Component
public class UserDao {

    @Resource
    private UserMapper userMapper;

    public List<UserModel> selectByName(String name) {
        QueryWrapper<UserModel> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("name", name);
        return userMapper.selectList(objectQueryWrapper);
    }
}
