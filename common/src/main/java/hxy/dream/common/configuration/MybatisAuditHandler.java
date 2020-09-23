package hxy.dream.common.configuration;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * mybatis-plus自动填充时间字段
 */
@Component
public class MybatisAuditHandler implements MetaObjectHandler {
    static final Logger LOG = LoggerFactory.getLogger(MybatisAuditHandler.class);

    @Override
    public void insertFill(MetaObject metaObject) {
        if (LOG.isDebugEnabled()){
            LOG.debug("\n====>mybatis自动填充创建时间字段");
        }
        // 声明自动填充字段的逻辑。
//        String userId = AuthHolder.getCurrentUserId();
        String userId = "";
        this.strictInsertFill(metaObject,"creator",String.class, userId);
        this.strictInsertFill(metaObject,"createTime", LocalDateTime.class,LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (LOG.isDebugEnabled()){
            LOG.debug("\n====>mybatis自动填充更新时间字段");
        }
        // 声明自动填充字段的逻辑。
//        String userId = AuthHolder.getCurrentUserId();
        String userId = "";
        this.strictUpdateFill(metaObject,"updater",String.class,userId);
        this.strictUpdateFill(metaObject,"updateTime", LocalDateTime.class,LocalDateTime.now());
    }
}