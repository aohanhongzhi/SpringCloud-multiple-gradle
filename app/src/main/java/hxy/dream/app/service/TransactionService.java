package hxy.dream.app.service;


import hxy.dream.dao.mapper.UserMapper;
import hxy.dream.dao.modle.UserModel;
import hxy.dream.entity.vo.BaseResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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
    public BaseResponseVO experiment() {
        saveParent();
        try {
            transactionSubService.saveChildren();
        } catch (Exception e) {
            log.error("====>\n", e);
        }
        log.info("====>\n运行完成了");
        return BaseResponseVO.success();
    }

    // ===========service实现类
    public void saveParent() {
        UserModel stu = new UserModel();
        stu.setName("parent");
        stu.setAge(19);
        userMapper.insert(stu); // 数据库插入一条parent记录
    }


}
