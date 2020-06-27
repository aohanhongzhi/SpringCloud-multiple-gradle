package hxy.dream.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum GenderEnum implements BaseEnum {
    BOY(1, "男"), GIRL(2, "女"),UNKNOWN(0, "未知");
    @EnumValue//标记数据库存的值是code
    private final Integer code;
    private final String description;

    GenderEnum(int code, String description) {
        this.code = code;
        this.description = description;
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
