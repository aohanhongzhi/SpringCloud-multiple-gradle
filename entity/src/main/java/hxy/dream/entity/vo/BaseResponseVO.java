package hxy.dream.entity.vo;

import lombok.Data;
import org.slf4j.MDC;

import java.io.Serializable;

@Data
public class BaseResponseVO<T> implements Serializable {
	private static final long serialVersionUID = -7956701197932947718L;
	private Long timestamp = System.currentTimeMillis();
	private String message;
	private T data;
	private Integer code;
	private String xid;

	public BaseResponseVO(Integer code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public static BaseResponseVO success() {
		return new BaseResponseVO(200, "success", null);
	}

	public static <T> BaseResponseVO<T> success(T data) {
		return new BaseResponseVO<T>(200, "success", data);
	}

	public static <T> BaseResponseVO<T> success(String message, T data) {
		return new BaseResponseVO<T>(200, message, data);
	}

	public static <T> BaseResponseVO<T> error(T data) {
		return new BaseResponseVO<T>(500, "error", data);
	}

	public static <T> BaseResponseVO<T> error(String message, T data) {
		return new BaseResponseVO<T>(500, message, data);
	}

	public static <T> BaseResponseVO<T> badrequest(String message, T data) {
		return new BaseResponseVO<T>(500, message, data);
	}

	/**
	 * 这个计划是由 spring-cloud-starter-sleuth
	 * 实现的。但是貌似spring-cloud-starter-sleuth没咋更新了，样例都是n年前的，还指向了zipkin。
	 * <p>
	 * 所以计划可以尝试用tlog实现下。https://tlog.yomahub.com/ https://github.com/dromara/TLog
	 */
	public String getXid() {
		String traceId = MDC.get("traceId");
		return traceId;
	}
}
