package hxy.dream.app.service;


import hxy.dream.dao.mapper.UserMapper;
import hxy.dream.dao.model.UserModel;
import hxy.dream.entity.vo.BaseResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;

/**
 * 事物测试
 *
 * @link https://blog.csdn.net/qq_34115899/article/details/115602002
 */
@Slf4j
@Service
public class TransactionService {

    @Resource
    UserMapper userMapper;

    @Resource
    TransactionSubService transactionSubService;

    /**
     * 测试事务
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public BaseResponseVO propagation() {
        saveParent();
        try {
            transactionSubService.saveChildren();
        } catch (Exception e) {
            log.error("====>\n异常直接捕获，但是还会抛出事务异常", e);
        }
//        int a = 1 / 0;
        log.info("====>\n事务测试运行完成了");
        return BaseResponseVO.success();
    }

    // ===========service实现类
    public void saveParent() {
        UserModel stu = new UserModel();
        stu.setName("parent");
        stu.setAge(19);
        userMapper.insert(stu); // 数据库插入一条parent记录
    }


    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public BaseResponseVO isolation() {
        int id = 1;
        UserModel userModel = userMapper.selectById(id);
        log.info("\n=====>第一次查询信息{}", userModel);

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 这里读到别人未提交的数据，也就是脏数据
        userModel = userMapper.selectById(id);
        log.info("\n=====>第二次查询信息{}，是脏数据", userModel);
        return BaseResponseVO.success();
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public BaseResponseVO isolation1() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        UserModel userModel = new UserModel();
        userModel.setId(1);
        userModel.setAge(9);
        userMapper.updateById(userModel);
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 发生异常回滚了
        int a = 1 / 0;

        return BaseResponseVO.success();
    }


}
