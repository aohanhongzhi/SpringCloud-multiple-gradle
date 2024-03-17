package hxy.dream.entity.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author eric
 * @date 9/5/19 9:58 PM
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseException extends RuntimeException {

    private String message;
    private int code = 0;

    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
        this.message = message;
    }

    public BaseException(int code, String message) {
        super(message);
        this.code = code;

    }

    public BaseException(int code, Throwable throwable) {
        super(throwable);
        this.code = code;
    }

    public BaseException(String message, Throwable throwable) {
        super(throwable);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getCode() {
        return this.code;
    }
}
