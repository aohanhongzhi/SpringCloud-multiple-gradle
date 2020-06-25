package hxy.dream.dao.modle;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

@Data
public class UserModel extends Model {
    String id;
    Enum gender;
    String password;
}
