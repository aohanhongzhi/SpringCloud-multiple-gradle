package hxy.dream.app.controller.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author eric
 * @program eric-dream
 * @description
 * @date 2/20/22
 */
@WebServlet(urlPatterns = "/AsyncRunningServlet2", asyncSupported = true)
public class AsyncRunningServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(AsyncRunningServlet.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType(MediaType.TEXT_HTML_VALUE);
        final AsyncContext asyncContext = req.startAsync();
        asyncContext.setTimeout(2 * 1000);
        final ServletInputStream inputStream = req.getInputStream();

        // 这里是开启一个新线程的
        inputStream.setReadListener(new MyTestReadListener(inputStream, asyncContext));

        log.info("1准备返回给前端");
        final PrintWriter writer = resp.getWriter();
        writer.println("直接返回给用户，异步处理可能还没有完成 response to user , async maybe not finished!");
        writer.flush();
        log.info("1已经返回给前端");

    }
}
