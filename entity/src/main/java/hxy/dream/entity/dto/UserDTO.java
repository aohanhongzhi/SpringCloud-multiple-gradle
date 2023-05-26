package hxy.dream.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Date;

@Data
public class UserDTO implements DTO {
    Integer id;
//    GenderEnum gender;

    public String name;

//    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8") // 这里就是单个注解的配置
    public Date registerDate;

    @Override
    public String dto() {
        return "";
    }
}
