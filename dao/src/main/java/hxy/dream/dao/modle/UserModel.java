package hxy.dream.dao.modle;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import hxy.dream.entity.enums.GenderEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("users")
@EqualsAndHashCode(callSuper=false)
public class UserModel extends Model {
    @TableId(type = IdType.AUTO)
    String id;
    String name;
    Integer age;
    GenderEnum gender;
    String password;
}
