package hxy.dream.app.controller.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author eric
 * @program eric-dream
 * @description
 * @date 2/20/22
 */
@WebServlet(urlPatterns = "/AsyncServlet", asyncSupported = true)
public class AsyncServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(AsyncServlet.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType(MediaType.TEXT_HTML_VALUE);
        final AsyncContext asyncContext = req.startAsync();
        asyncContext.setTimeout(2 * 1000);

        // 这里是开启一个新线程的，生产使用线程池更好，这样就做到了Tomcat的IO线程与业务线程的分离，能提高吞吐量！
        new Thread(new BusinessRunnable(req, resp)).start();

    }

    class BusinessRunnable implements Runnable {
        HttpServletRequest req;
        HttpServletResponse resp;

        BusinessRunnable(HttpServletRequest req, HttpServletResponse resp) {
            this.req = req;
            this.resp = resp;
        }

        @Override
        public void run() {
            final PrintWriter writer;
            try {
                writer = this.resp.getWriter();
                writer.write("异步的业务线程返回数据 async thread response data");
                writer.flush();
            } catch (IOException e) {
                log.error("{}", e.getMessage(), e);
            }
        }
    }
}
