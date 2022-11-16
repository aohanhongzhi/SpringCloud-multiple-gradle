package hxy.dream.common.extend;

import hxy.dream.entity.exception.BaseException;
import hxy.dream.entity.exception.WebException;
import hxy.dream.entity.vo.BaseResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.io.IOException;
import java.util.Map;

/**
 * Description:全局异常处理，采用@RestControllerAdvice + @ExceptionHandler解决
 * <br>自定义异常处理类
 *
 * @author eric
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler implements InitializingBean {

//    查看这个类  hxy.dream.common.configuration.WrapperErrorAttributes
//    /**
//     * Hide exception field in the return object
//     *
//     * @return
//     */
//    @Bean
//    @Profile("prod")
//    public ErrorAttributes errorAttributes() {
//        return new DefaultErrorAttributes() {
//            @Override
//            public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
//                return super.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults().excluding(ErrorAttributeOptions.Include.EXCEPTION));
//            }
//        };
//    }

    @ExceptionHandler(WebException.class)
    public void handleCustomException(HttpServletResponse res, WebException ex) throws IOException {
        // 这里可以设置status，但是还不能设置 status code
        res.sendError(ex.getHttpStatus().value(), ex.getMessage());
    }


    /**
     * 参数校验异常捕获 包括各种自定义的参数异常
     *
     * @param request
     * @param e
     * @param <T>fsFileId
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public <T> BaseResponseVO<?> constraintViolationExceptionHandler(HttpServletRequest request, ConstraintViolationException e) {
        log.warn("\n====>{} Exception Message: {}", request.getRequestURI(), e.getMessage(), e);
        return BaseResponseVO.error("参数错误", e.getMessage());
    }

    /**
     * 处理异常的访问链接
     *
     * @param e
     * @param webRequest
     * @return
     */
    @ExceptionHandler(value = {NoHandlerFoundException.class})
    public ModelAndView noMappingHandler(Exception e, WebRequest webRequest) {
        log.error("No mapping exception:{}", e.getMessage(), e);
        ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());
        modelAndView.addObject("code", 404);
        modelAndView.addObject("msg", "接口异常，详情查询服务端");
        modelAndView.addObject("data", e.getMessage());
        return modelAndView;
    }

    /**
     * 方法参数校验
     * https://blog.csdn.net/chengliqu4475/article/details/100834090
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public <T> BaseResponseVO<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn(e.getMessage(), e);
        return BaseResponseVO.error("参数检验出错啦！", e.getBindingResult().getFieldError().getDefaultMessage());
    }


    /**
     * 处理400参数错误
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public BaseResponseVO handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.warn("参数解析失败{}", e.getMessage(), e);
        return BaseResponseVO.badrequest("参数解析失败", e.getMessage());
    }

    /**
     * 405错误方法不支持
     *
     * @param e
     * @return
     */
    @ResponseStatus(org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public BaseResponseVO handleHttpRequestMethodNotSupportedException(HttpServletRequest request, HttpRequestMethodNotSupportedException e) {
        log.warn("\n====>[{}]不支持当前[{}]请求方法,应该是[{}]", request.getRequestURI(), e.getMethod(), e.getSupportedMethods());
        return BaseResponseVO.error("请求方法不支持", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public BaseResponseVO MissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.warn("\n====>请求参数错误{}", e.getMessage(), e);
        return BaseResponseVO.badrequest("请求参数错误", e.getMessage());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public BaseResponseVO handleDuplicateKeyException(DuplicateKeyException e) {
        log.warn("\n====>", e);
        return BaseResponseVO.badrequest("请求参数错误", e.getMessage());

    }

    /**
     * 这个应该放在最下面比较好，最后加载
     * 处理未定义的其他异常信息
     * 参数为空等
     *
     * @param request
     * @param exception
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public BaseResponseVO exceptionHandler(HttpServletRequest request, Exception exception) {
        String message = "系统发生错误";
        log.error("当前URL={} ", request.getRequestURI(), exception);

        if (exception instanceof NullPointerException) {
            message = "biu，踩雷啦！";
        } else if (exception instanceof ValidationException) {
            message = "参数检验出错啦！";
        } else if (exception instanceof BaseException) {
            message = "业务处理发生错误";
        }

        return BaseResponseVO.error(message, exception.getMessage());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("\n====>全局异常注入正常");
    }

    @PostConstruct
    public void init() {
        log.info("\n====>@PostConstruct 全局异常注入正常");
    }
}

