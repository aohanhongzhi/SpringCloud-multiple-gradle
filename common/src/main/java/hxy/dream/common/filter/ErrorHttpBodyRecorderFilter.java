package hxy.dream.common.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.annotation.WebFilter;

/**
 * https://github.com/aohanhongzhi/httpBodyRecorder
 */
@WebFilter
@Component
public class ErrorHttpBodyRecorderFilter extends HttpBodyRecorderFilter {
    private static final Logger log = LoggerFactory.getLogger(ErrorHttpBodyRecorderFilter.class);

    @Override
    protected void recordBody(String payload, String response) {
        log.info("请求参数 {}，返回参数 {}", payload, response);
    }

    @Override
    protected String recordCode() {
        // 一般记录错误的参数即可
        return "200,400,500"; //record when code in [400,500]
    }
}
