package hxy.dream.app.controller.servlet;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

/**
 * @author eric
 * @program eric-dream
 * @description
 * @date 2/20/22
 */
public class MyTestReadListener implements ReadListener {
    private static final Logger log = LoggerFactory.getLogger(MyTestReadListener.class);


    private ServletInputStream servletInputStream;
    private AsyncContext asyncContext;

    public MyTestReadListener(ServletInputStream servletInputStream, AsyncContext asyncContext) {
        this.servletInputStream = servletInputStream;
        this.asyncContext = asyncContext;
    }

    @Override
    public void onDataAvailable() throws IOException {
        log.info("执行到我了，数据可用");
        StringBuilder content = new StringBuilder();
        final byte[] b = new byte[1024];
        int c = servletInputStream.read(b);
        log.info("read: " + c);
        if (c > 0) {
            content.append(new String(b, 0, c));
        }
        boolean ready = servletInputStream.isReady();
        if (ready) {
            log.info("准备好了数据");
            onDataAvailable();
        } else {
            log.info("没有准备好或者没有数据了");
        }
        log.info("请求体的参数 => {}", content);
    }

    @Override
    public void onAllDataRead() throws IOException {
        try {
            TimeUnit.SECONDS.sleep(1);
            final PrintWriter writer = asyncContext.getResponse().getWriter();
            writer.write("完成业务处理，返回正常.process success!");
            writer.flush();
            // 这里2和之前的1是一起返回的
            log.info("2已经返回给前端");
        } catch (InterruptedException e) {
            log.error("{}", e.getMessage(), e);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("读取数据出错了{}", throwable.getMessage(), throwable);
    }
}
