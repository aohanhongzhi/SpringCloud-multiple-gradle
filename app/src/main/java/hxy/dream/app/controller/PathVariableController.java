package hxy.dream.app.controller;

import hxy.dream.entity.vo.BaseResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PathVariableController {

    private static final Logger log = LoggerFactory.getLogger(PathVariableController.class);


    /**
     * @param variable
     * @return
     * @see org.springframework.web.servlet.handler.AbstractHandlerMethodMapping#addMatchingMappings(java.util.Collection, java.util.List, jakarta.servlet.http.HttpServletRequest)
     */
    @GetMapping("/path/variable/{variable}")
    public BaseResponseVO path(@PathVariable(value = "variable") String variable) {
        log.info("path: " + variable);
        return BaseResponseVO.success(variable);
    }
}
