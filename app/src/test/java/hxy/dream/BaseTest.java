package hxy.dream;

import hxy.dream.app.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * 使用 @ActiveProfiles 注解，可以覆盖掉yaml文件的指定配置环境，方便单元测试指定环境。又不影响线上部署
 */
@ActiveProfiles("test")
@SpringBootTest(classes = Application.class)
public abstract class BaseTest {
   public static final Logger log = LoggerFactory.getLogger(BaseTest.class);
}
