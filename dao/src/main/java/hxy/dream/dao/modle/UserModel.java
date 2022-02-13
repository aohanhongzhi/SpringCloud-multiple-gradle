package hxy.dream.dao.modle;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import hxy.dream.entity.enums.GenderEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author iris
 */
@Data
@TableName("user_model")
@EqualsAndHashCode(callSuper=false)
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
}
