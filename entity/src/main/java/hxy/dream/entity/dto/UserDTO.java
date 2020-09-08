package hxy.dream.entity.dto;

import lombok.Data;

@Data
public class UserDTO implements DTO {
    String id;
//    GenderEnum gender;

    @Override
    public String dto() {

        return id;
    }
}
