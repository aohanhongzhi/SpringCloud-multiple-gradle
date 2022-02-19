package hxy.dream.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author eric
 * @program multi-gradle
 * @description
 * @date 2022/2/19
 */
@RestController
public class ExceptionController {

    @RequestMapping("/exception")
    public void exception(HttpServletResponse httpServletResponse) {

        try {
            httpServletResponse.sendError(666, "测试换回的是html还是json，结论是取决于客户端");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
