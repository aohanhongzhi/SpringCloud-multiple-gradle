package hxy.dream.common.util;

import hxy.dream.BaseTest;
import org.junit.jupiter.api.Test;

/**
 * @author eric
 * @description
 * @date 2024/3/17
 */
public class UnitTestEnvironmentTest extends BaseTest {

    @Test
    public void testEnvironmentUtils() {
        String activeProfile = EnvironmentUtils.getActiveProfile();
        log.info("{}", activeProfile);
    }

}
