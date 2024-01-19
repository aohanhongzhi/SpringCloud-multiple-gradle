package hxy.dream.common.configuration;


import hxy.dream.common.manager.RemoteApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration(proxyBeanMethods = false)
public class RemoteApiConfig {

    @Bean
    RemoteApi remoteApiService() {
        WebClient client = WebClient.builder().baseUrl("https://httpbin.org/").build();
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(WebClientAdapter.create(client)).build();
        // 后期会自动扫描注入，不需要手动指定注入了。但是上面host配置肯定还是需要的。
        return httpServiceProxyFactory.createClient(RemoteApi.class);
    }

}
