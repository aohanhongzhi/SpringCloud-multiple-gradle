package hxy.dream.dao.configuration.mybatis;

/**
 * @ClassName AESTypeHandler
 * @date 2020/4/9  14:27.
 * @Description
 */

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import hxy.dream.dao.util.KeyCenterUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 数据库字段加解密处理
 *
 * @param <T>
 */
@Service
public class CustomTypeHandler<T> extends BaseTypeHandler<T> {


    public CustomTypeHandler() {
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, KeyCenterUtils.encrypt((String) parameter));
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String columnValue = rs.getString(columnName);
        //有一些可能是空字符
        return StringUtils.isBlank(columnValue) ? (T) columnValue : (T) KeyCenterUtils.decrypt(columnValue);
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String columnValue = rs.getString(columnIndex);
        return StringUtils.isBlank(columnValue) ? (T) columnValue : (T) KeyCenterUtils.decrypt(columnValue);
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String columnValue = cs.getString(columnIndex);
        return StringUtils.isBlank(columnValue) ? (T) columnValue : (T) KeyCenterUtils.decrypt(columnValue);
    }
}


