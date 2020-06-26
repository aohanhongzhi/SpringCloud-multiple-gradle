package hxy.dream.dao.modle;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

@Data
@TableName("users")
public class UserModel extends Model {
    String id;
    String name;
    Integer age;
    Enum gender;
    String password;
}
