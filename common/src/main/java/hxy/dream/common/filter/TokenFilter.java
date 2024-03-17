package hxy.dream.common.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;

import java.io.IOException;

/**
 * @author iris
 */
@Order(1)
@WebFilter(filterName = "TokenFilter", urlPatterns = "/*", initParams = {
        @WebInitParam(name = "URL", value = "http://localhost:8080")}, asyncSupported = true)
public class TokenFilter implements Filter {

    Logger logger = LoggerFactory.getLogger(TokenFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("\n====>TokenFilter初始化配置信息[{}]", filterConfig);
    }

    /**
     * 这里就是获取token，提取用户信息到SpringSecurity安全上下文中
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        logger.info("\n====>TokenFilter过滤器使用[{}]", chain);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        logger.info("\n====>TokenFilter销毁");
    }
}
