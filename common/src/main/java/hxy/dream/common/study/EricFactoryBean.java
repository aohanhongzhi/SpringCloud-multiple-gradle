package hxy.dream.common.study;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @author eric
 * @program eric-dream
 * @description
 * @date 2021/8/29
 */
@Component
public class EricFactoryBean implements FactoryBean<EricService> {
    @Override
    public EricService getObject() throws Exception {
        return new EricService();
    }

    @Override
    public Class<?> getObjectType() {
        return EricService.class;
    }
}
