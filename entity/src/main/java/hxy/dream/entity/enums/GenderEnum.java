package hxy.dream.entity.enums;


import com.baomidou.mybatisplus.annotation.EnumValue;

import java.util.Arrays;
import java.util.Optional;


public enum GenderEnum implements BaseEnum {

    BOY(100, "男"), GIRL(200, "女"), UNKNOWN(0, "未知");

    @EnumValue//标记数据库存的值是code
    private final Integer code;

    private final String description;

    GenderEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static GenderEnum getEnumByCode(Integer code) {
//        for (GenderEnum genderEnum:values()){
//            if (genderEnum.code.equals(code)){
//                return genderEnum;
//            }
//        }
        Optional<GenderEnum> first = Arrays.stream(GenderEnum.values()).filter(item -> item.code().equals(code)).findFirst();
        if (first.isEmpty()) {
            return null;
        } else {
            GenderEnum status = first.get();
            return status;
        }
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
