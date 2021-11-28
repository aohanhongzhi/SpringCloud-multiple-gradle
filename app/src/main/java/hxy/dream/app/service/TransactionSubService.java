package hxy.dream.app.service;

import hxy.dream.dao.mapper.UserMapper;
import hxy.dream.dao.modle.UserModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author eric
 * @program multi-gradle
 * @description
 * @date 2021/11/28
 */
@Service
public class TransactionSubService {

    @Resource
    UserMapper userMapper;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void saveChildren() {
        saveChild1();
        int a = 1 / 0;
        saveChild2();
    }

    public void saveChild1() {
        UserModel stu1 = new UserModel();
        stu1.setName("child-1");
        stu1.setAge(11);
        userMapper.insert(stu1); // 数据库插入一条child-1记录
    }

    public void saveChild2() {
        UserModel stu2 = new UserModel();
        stu2.setName("child-2");
        stu2.setAge(22);
        userMapper.insert(stu2); // 数据库插入一条child-2记录
    }
}
