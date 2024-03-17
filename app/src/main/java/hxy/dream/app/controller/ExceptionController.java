package hxy.dream.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author eric
 * @program multi-gradle
 * @description
 * @date 2022/2/19
 */
@RestController
public class ExceptionController {

    private static final Logger log = LoggerFactory.getLogger(ExceptionController.class);

    @RequestMapping("/exception")
    public void exception(HttpServletResponse httpServletResponse) {

        try {
            httpServletResponse.sendError(666, "测试换回的是html还是json，结论是取决于客户端");
        } catch (IOException e) {
            log.error("{}", e.getMessage(), e);
        }
    }

    @RequestMapping("/exception-output")
    public void exceptionOutput() {
        throw new IllegalArgumentException("异常啊");
    }

}
