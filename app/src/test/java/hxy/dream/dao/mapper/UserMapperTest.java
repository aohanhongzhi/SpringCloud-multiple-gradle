package hxy.dream.dao.mapper;

import hxy.dream.BaseTest;
import hxy.dream.dao.model.UserModel;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author eric
 * @description
 * @date 2023/6/5
 */
public class UserMapperTest extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(UserMapperTest.class);


    @Resource
    UserMapper userMapper;

    @Test
    public void testUpdateNull() {

        UserModel userModel = new UserModel();
        userModel.setId(1);
        userModel.setAge(23);
        userModel.setName(null);
//        https://blog.csdn.net/konnysnow/article/details/121421210
//        当然也可以用 lambdaUpdateWrapper.set(userModel::getName, null);
        int i = userMapper.updateById(userModel);
        log.info("Updated {}", i);
    }

}
