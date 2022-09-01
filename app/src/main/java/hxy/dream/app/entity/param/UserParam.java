package hxy.dream.app.entity.param;

import hxy.dream.entity.enums.GenderEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author iris
 */
@Data
public class UserParam {
    @NotBlank(message = "name不能为空")
    String name;
    @NotNull(message = "gender为100或者200")
    GenderEnum gender;

    @NotNull(message = "age不能为空")
    Integer age;

    String address;
}
