package hxy.dream.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponseVO<T> implements Serializable {
    Long timestamp = System.currentTimeMillis();
    String message;
    T data;
    Integer code;

    public BaseResponseVO(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static BaseResponseVO success() {
        return new BaseResponseVO(200, "success", null);
    }

    public static <T> BaseResponseVO<T> success(T data) {
        return new BaseResponseVO(200, "success", data);
    }
}
