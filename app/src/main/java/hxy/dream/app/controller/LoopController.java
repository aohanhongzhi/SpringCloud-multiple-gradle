package hxy.dream.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author eric
 * @program multi-gradle
 * @description 死循环代码
 * @date 2022/1/25
 */
@RestController
@RequestMapping("/loop")
public class LoopController {

    @RequestMapping("/while")
    public void loop() {
        // 死循环代码
        while (true) {
        }
    }
}
