package hxy.dream.entity.exception;

import org.springframework.http.HttpStatus;

/**
 * @author eric
 * @program multi-gradle
 * @description
 * @date 2022/2/19
 */
public class WebException extends BaseException {

    private static final long serialVersionUID = -2175679614507721010L;
	private HttpStatus httpStatus;
    private String message;

    public WebException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    WebException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

}
