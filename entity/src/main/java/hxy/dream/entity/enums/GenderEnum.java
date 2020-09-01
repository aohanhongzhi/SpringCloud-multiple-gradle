package hxy.dream.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
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

//    @JsonValue
    @Override
    public Integer code() {
        return code;
    }

    @Override
    public String description() {
        return description;
    }

//    @JsonCreator
//    public static GenderEnum getItem(int code){
//        for(GenderEnum item : values()){
//            if(item.getCode() == code){
//                return item;
//            }
//        }
//        return null;
//    }

}
