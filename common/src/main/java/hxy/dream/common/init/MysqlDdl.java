package hxy.dream.common.init;

import com.baomidou.mybatisplus.extension.ddl.SimpleDdl;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * https://baomidou.com/pages/1812u1/
 * 数据库 DDL 表结构执行 SQL 自动维护功能
 */
@Component
public class MysqlDdl extends SimpleDdl {

    /**
     * 执行 SQL 脚本方式
     */
    @Override
    public List<String> getSqlFiles() {
        return Arrays.asList(
                "table.sql"
        );
    }
}