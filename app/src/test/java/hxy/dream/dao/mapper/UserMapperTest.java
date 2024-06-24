package hxy.dream.dao.mapper;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
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
    public void testInsertOrUpdateSetNull() {
        UserModel userModel = new UserModel();
        userModel.setId(1);
        userModel.setAge(231);
        boolean update = userMapper.insertOrUpdate(userModel);
        log.info("Updated user{}", update);
    }


    @Test
    public void testUpdateNull() {

        UserModel userModel = new UserModel();
        userModel.setId(1);
        userModel.setAge(23);
        userModel.setName(null); // mybatis-plus是默认不更新 null的属性
//        https://blog.csdn.net/konnysnow/article/details/121421210
//        当然也可以用 lambdaUpdateWrapper.set(userModel::getName, null);
        int i = userMapper.updateById(userModel);
        log.info("Updated {}", i);
    }

    @Test
    public void testUpdateSetNull() {
        UserModel userModel = new UserModel();
        userModel.setId(1);
        userModel.setAge(231);

        LambdaUpdateWrapper<UserModel> lambdaUpdateWrapper = new LambdaUpdateWrapper<UserModel>();
        lambdaUpdateWrapper.set(UserModel::getName, null); // 强制修改数据库为null
        lambdaUpdateWrapper.eq(UserModel::getId, userModel.getId());

        int update = userMapper.update(userModel, lambdaUpdateWrapper);
        log.info("Updated user{}", update);

    }

    @Test
    public void deleteWithoutLogicDelete() {
        UserModel userModel = new UserModel();
        userModel.setId(1);
        userModel.setAge(2);
        userModel.setName("222");
        userMapper.updateWithoutLogicDelete(userModel);
    }

}
