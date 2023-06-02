package hxy.dream.common.manager;


import hxy.dream.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class RemoteApiTest extends BaseTest {


    @Autowired
    private RemoteApi remoteApi;

    @Test
    public void testGet() {
        String body = remoteApi.getBody();
        log.info("{}", body);
    }


}
