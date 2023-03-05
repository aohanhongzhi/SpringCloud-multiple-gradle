package hxy.dream.common.filter;

import hxy.dream.common.filter.wrapper.RequestTrimHttpServletRequestWrapper;
import jakarta.servlet.Filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 请求参数过滤器
 */
public class RequestTrimFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(new RequestTrimHttpServletRequestWrapper((HttpServletRequest) servletRequest), servletResponse);
    }

}
