package hxy.dream.app.controller;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import hxy.dream.entity.vo.BaseResponseVO;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HOX4SGH
 * @description
 * @date 2024/10/19
 */
@RestController
public class LogResetController {

    /**
     * /log/set?name=hxy.dream.app&level=info
     *
     * @param name
     * @param level
     * @return
     */
    @GetMapping("/log/set")
    public BaseResponseVO setLog(String name, String level) {
        Logger logger = (Logger) LoggerFactory.getLogger(name);
        logger.setLevel(Level.toLevel(level));
        return BaseResponseVO.success();
    }
}
