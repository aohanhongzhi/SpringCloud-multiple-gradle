package hxy.dream.dao.modle;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import hxy.dream.dao.configuration.mybatis.CustomTypeHandler;
import hxy.dream.entity.enums.GenderEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author iris
 */
@Data
@TableName(value = "user_model", autoResultMap = true)  //有了这个BaseMapper查询的结果才能解密
@EqualsAndHashCode(callSuper = false)
public class UserModel extends BaseModel<UserModel> {
    /**
     * 参考yaml文件配置id-type: auto
     */
    @TableId(type = IdType.AUTO)
    Integer id;
    String name;
    Integer age;
    GenderEnum gender;
    String password;

    //有了这个数据库BaseMapper插入的时候才能加密
    @TableField(typeHandler = CustomTypeHandler.class)
    String address;
}
