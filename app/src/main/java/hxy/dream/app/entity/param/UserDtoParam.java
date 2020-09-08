package hxy.dream.app.entity.param;

import hxy.dream.entity.dto.UserDTO;
import hxy.dream.entity.enums.GenderEnum;
import lombok.Data;

/**
 * @author iris
 */
@Data
public class UserDtoParam {
    UserDTO userDTO;
    GenderEnum genderEnum;
}
