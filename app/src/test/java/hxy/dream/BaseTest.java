package hxy.dream;

import hxy.dream.app.Application;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
//@RunWith(SpringJUnit4ClassRunner.class)
public abstract class BaseTest {
}
