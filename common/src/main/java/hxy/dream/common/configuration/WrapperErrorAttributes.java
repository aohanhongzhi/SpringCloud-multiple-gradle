package hxy.dream.common.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
import java.util.Set;

/**
 * 这个类并不能决定SpringBoot返回是html还是json。这个类的主要作用在返回json的时候增加或者修改一些自定义属性
 * 其实SpringBoot本来就有两种返回方式--html和json。至于返回啥取决于客户端的访问方式中是否带有 Content-Type=text/html
 *
 * @link https://docs.qq.com/doc/DSExnbXFSS0Nub0V3  【腾讯文档】500,404返回json数据的增加或者修改
 * @see org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController
 */
@Component
@Slf4j
@Profile(value = {"prod"})
public class WrapperErrorAttributes extends DefaultErrorAttributes {
    /**
     * 返回json 403 500
     *
     * @param webRequest
     * @param options
     * @return
     */
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
        log.debug("\n=====>初始：[{}]", errorAttributes);

//        errorAttributes.remove("timestamp");
//        errorAttributes.remove("path");

        Set<String> strings = errorAttributes.keySet();
        for (String it : strings) {
            Object o = errorAttributes.get(it);
            log.debug("返回error信息key :{} value : {}", it, o);
        }

        errorAttributes.put("user", "Eric");
        log.debug("\n=====>结果：[{}]", errorAttributes);
        return errorAttributes;
    }

}
