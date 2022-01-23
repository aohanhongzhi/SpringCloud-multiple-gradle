package hxy.dream.common.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.MultipartConfigElement;

/**
 * @author eric
 * @program multi-gradle
 * @description
 * @date 2022/1/23
 */
@Configuration
public class AsyncConfig {
//    @Bean
//    public ServletRegistrationBean dispatcherServlet() {
//        ServletRegistrationBean registration = new ServletRegistrationBean(new DispatcherServlet(), "/");
//        registration.setAsyncSupported(true);
//        return registration;
//    }
//
//    @Autowired
//    private WebMvcProperties webMvcProperties;
//
//    @Autowired(required = false)
//    private MultipartConfigElement multipartConfig;

//    @Bean
//    @Primary
//    public DispatcherServletRegistrationBean dispatcherServlet1( ) {
//        DispatcherServletRegistrationBean registration = new DispatcherServletRegistrationBean(
//                new DispatcherServlet(), "/*");
////        registration.setName("dispatcherServlet1");
//        registration.setAsyncSupported(true);
//        registration.setLoadOnStartup(
//                this.webMvcProperties.getServlet().getLoadOnStartup());
//        if (this.multipartConfig != null) {
//            registration.setMultipartConfig(this.multipartConfig);
//        }
//        return registration;
//    }
}
