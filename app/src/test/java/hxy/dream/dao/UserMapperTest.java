package hxy.dream.dao;

import hxy.dream.dao.mapper.UserMapper;
import hxy.dream.dao.model.UserModel;
import org.junit.Test;

/**
 * @author eric
 * @description
 * @date 2022/6/3
 */
public class UserMapperTest extends hxy.dream.BaseTest {


    @javax.annotation.Resource
    UserMapper userMapper;

    @Test
    public void Atest() {
        UserModel userModel = new UserModel();
        userModel.setId(3);
        userModel.setName("AA");
        userModel.setPhone(12111L);
        userMapper.insert(userModel);
    }


}
