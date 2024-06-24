package hxy.dream.dao.mybatis;

import hxy.dream.dao.mapper.UserMapper;
import hxy.dream.dao.model.UserModel;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Reader;
import java.util.Arrays;

/**
 * @author eric
 * @description
 * @date 2024/6/24
 */
public class PureMybatisTest {

    private static final Logger log = LoggerFactory.getLogger(PureMybatisTest.class);


    static SqlSessionFactory sqlSessionFactory;

    @BeforeAll
    public static void beforeAll() {
        String resource = "mybatis-config.xml";
        try (Reader reader = Resources.getResourceAsReader(resource)) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void insertMultipleUsers2() {
        UserModel userModel = new UserModel();
        userModel.setId(1);
        userModel.setAge(111);

        UserModel userModel1 = new UserModel();
        userModel1.setId(2);
        userModel1.setAge(222);


        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
//            同一个session，没有提交就是在同一个Transaction里面
            for (UserModel user : Arrays.asList(userModel, userModel1)) {
                int i = userMapper.updateWithoutLogicDelete(user);
                log.info("Updated {}", i);
            }
            session.commit();  // 提交事务
        } catch (Exception e) {
            e.printStackTrace();
        }
        // session 会自动close
    }
}
