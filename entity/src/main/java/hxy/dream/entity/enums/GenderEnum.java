package hxy.dream.entity.enums;

public enum GenderEnum implements Enumerator {
    BOY(1, "男"), GIRL(2, "女"),UNKNOWN(0, "未知");

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
