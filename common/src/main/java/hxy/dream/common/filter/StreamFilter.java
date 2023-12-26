package hxy.dream.common.filter;

import hxy.dream.common.filter.wrapper.RepeatedlyReadRequestWrapper;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 过滤器封装下request再传到后续处理
 */
public class StreamFilter implements Filter {

    /**
     * 不需要重复读取的路由
     */
    private final Set<String> IGNORE_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/api/file/upload")));

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;

        if (request instanceof HttpServletRequest) {
            HttpServletRequest servletRequest = (HttpServletRequest) request;
            String requestURI = servletRequest.getRequestURI();
            if (IGNORE_PATHS.contains(requestURI)) {
                // 文件就不用重复读取了
                chain.doFilter(servletRequest, response);
            } else {
                requestWrapper = new RepeatedlyReadRequestWrapper(servletRequest);
                //获取请求中的流如何，将取出来的字符串，再次转换成流，然后把它放入到新request对象中。
                // 在chain.doFiler方法中传递新的request对象
                chain.doFilter(requestWrapper, response);
            }
        }

    }

    @Override
    public void destroy() {

    }
}