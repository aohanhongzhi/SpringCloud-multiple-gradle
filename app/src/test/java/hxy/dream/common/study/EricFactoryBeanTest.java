package hxy.dream.common.study;

import hxy.dream.BaseTest;
import hxy.dream.app.Application;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author eric
 * @program eric-dream
 * @description
 * @date 2021/8/29
 */
public class EricFactoryBeanTest extends BaseTest {
    @Test
    public void println(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Application.class);
        final Object ericFactoryBean = applicationContext.getBean("ericFactoryBean");
        System.out.println(ericFactoryBean);
    }
}
