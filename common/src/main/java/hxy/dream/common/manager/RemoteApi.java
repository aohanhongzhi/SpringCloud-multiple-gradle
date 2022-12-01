package hxy.dream.common.manager;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

/**
 * 通过RPC/Http调用远程的服务请求
 * @author iris
 */
@HttpExchange
public interface RemoteApi {

    /**
     * @return 声明式HTTP客户端
     */
    @GetExchange("/get")
    String getBody();

}
