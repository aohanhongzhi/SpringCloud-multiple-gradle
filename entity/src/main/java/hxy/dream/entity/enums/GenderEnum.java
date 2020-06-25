package hxy.dream.entity.enums;

public enum GenderEnum {
    BOY(1, "男"), GIRL(2, "女");

    int code;
    String value;

    GenderEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
