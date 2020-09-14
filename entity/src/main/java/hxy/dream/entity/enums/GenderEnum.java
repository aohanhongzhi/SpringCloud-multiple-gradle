package hxy.dream.entity.enums;

import ch.qos.logback.classic.boolex.GEventEvaluator;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum GenderEnum implements BaseEnum {
    BOY(100, "男"), GIRL(200, "女"),UNKNOWN(0, "未知");


    @EnumValue//标记数据库存的值是code
    private final Integer code;

    private final String description;

    GenderEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }


    public static GenderEnum getEnumByCode(Integer code){
        for (GenderEnum genderEnum:values()){
            if (genderEnum.code.equals(code)){
                return genderEnum;
            }
        }
        return null;
    }

    @Override
    public Integer code() {
        return code;
    }

    @Override
    public String description() {
        return description;
    }


}
