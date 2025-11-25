package hxy.dream.common.init;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * https://baomidou.com/pages/1812u1/
 * 数据库 DDL 表结构执行 SQL 自动维护功能
 */
@Component
@Slf4j
public class MysqlDdl {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 执行 SQL 脚本方式
     */
    public List<String> getSqlFiles() {
        return Arrays.asList(
                "table.sql"
        );
    }

    @PostConstruct
    public void initTable() {
        try {
            for (String sqlFile : getSqlFiles()) {
                ClassPathResource resource = new ClassPathResource(sqlFile);
                if (resource.exists()) {
                    String sqlScript = FileCopyUtils.copyToString(new InputStreamReader(
                            resource.getInputStream(), StandardCharsets.UTF_8));
                    // 执行SQL脚本
                    String[] sqlStatements = sqlScript.split(";");
                    for (String sql : sqlStatements) {
                        sql = sql.trim();
                        if (!sql.isEmpty()) {
                            jdbcTemplate.execute(sql);
                            log.info("执行SQL: {}", sql.substring(0, Math.min(100, sql.length())) + (sql.length() > 100 ? "..." : ""));
                        }
                    }
                    log.info("SQL脚本 {} 执行完成", sqlFile);
                } else {
                    log.warn("SQL脚本文件不存在: {}", sqlFile);
                }
            }
        } catch (Exception e) {
            log.error("执行SQL脚本时出错", e);
            throw new RuntimeException("执行SQL脚本失败", e);
        }
    }
}