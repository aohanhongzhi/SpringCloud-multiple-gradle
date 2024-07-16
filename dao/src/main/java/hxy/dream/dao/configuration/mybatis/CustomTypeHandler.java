package hxy.dream.dao.configuration.mybatis;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import hxy.dream.dao.util.AesCbcEncryption;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 数据库字段加解密处理。
 * 加密后就有一个问题，没办法对数据进行检索了。比如like查询。但是mybatis-plus提供了自己企业版的加密方式可以做到。https://baomidou.com/pages/1864e1/#%E5%AD%97%E6%AE%B5%E5%8A%A0%E5%AF%86%E8%A7%A3%E5%AF%86
 *
 * @param <T>
 */
public class CustomTypeHandler<T> extends BaseTypeHandler<T> {

    private final String AES_PREFIX = "{aes}";

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        String encrypt = (String) parameter;
        if (parameter != null) {
            encrypt = AesCbcEncryption.encrypt((String) parameter);
            encrypt = AES_PREFIX + encrypt;
        }
        ps.setString(i, encrypt);
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String columnValue = rs.getString(columnName);
        if (columnValue != null && columnValue.startsWith(AES_PREFIX)) {
            columnValue = columnValue.replace(AES_PREFIX, "");
            //有一些可能是空字符
            return StringUtils.isBlank(columnValue) ? (T) columnValue : (T) AesCbcEncryption.decrypt(columnValue);
        }
        return (T) columnValue;
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String columnValue = rs.getString(columnIndex);
        if (columnValue != null && columnValue.startsWith(AES_PREFIX)) {
            columnValue = columnValue.replace(AES_PREFIX, "");
            return StringUtils.isBlank(columnValue) ? (T) columnValue : (T) AesCbcEncryption.decrypt(columnValue);
        }
        return (T) columnValue;
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String columnValue = cs.getString(columnIndex);
        if (columnValue != null && columnValue.startsWith(AES_PREFIX)) {
            columnValue = columnValue.replace(AES_PREFIX, "");
            return StringUtils.isBlank(columnValue) ? (T) columnValue : (T) AesCbcEncryption.decrypt(columnValue);
        }
        return (T) columnValue;
    }
}


