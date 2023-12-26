package hxy.dream.common.configuration;

import hxy.dream.common.filter.RequestTrimFilter;
import hxy.dream.common.filter.RepeatableFilter;
import hxy.dream.common.filter.TokenFilter;
import jakarta.servlet.DispatcherType;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {

    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new TokenFilter());
        registration.addUrlPatterns("/*");
        registration.setAsyncSupported(true);
        registration.setName("tokenFilter");
        registration.setOrder(FilterRegistrationBean.LOWEST_PRECEDENCE);
        return registration;
    }

    /**
     * 添加去除参数头尾空格过滤器
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean trimFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new RequestTrimFilter());
        registration.addUrlPatterns("/*");
        registration.setName("requestTrimFilter");
        registration.setOrder(Integer.MAX_VALUE - 1);
        return registration;
    }

    /**
     * 让request可重复读取
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean streamFilter() {
        FilterRegistrationBean streamBean = new FilterRegistrationBean();
        streamBean.setDispatcherTypes(DispatcherType.REQUEST);
        streamBean.setFilter(new RepeatableFilter());
        streamBean.setName("repeatableFilter");
        streamBean.setOrder(FilterRegistrationBean.LOWEST_PRECEDENCE);
        return streamBean;
    }
}
