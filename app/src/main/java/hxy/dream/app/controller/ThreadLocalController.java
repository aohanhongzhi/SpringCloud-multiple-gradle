package hxy.dream.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author aohan
 * @program multi-gradle
 * @description 本地线程
 * @date 2022/3/17
 */
@RequestMapping("threadlocal")
@RestController
public class ThreadLocalController {

    // Thread里面有一个属性 ThreadLocal.ThreadLocalMap ，下面两个ThreadLocal，就会作为key存储在Map当中
    private static final ThreadLocal<Integer> currentUser = ThreadLocal.withInitial(() -> null);
    private static final ThreadLocal<Long> timeCounter = ThreadLocal.withInitial(() -> null);

    @GetMapping("wrong")
    public Map wrong(@RequestParam("userId") Integer userId) {
        //设置用户信息之前先查询一次ThreadLocal中的用户信息
        String before = Thread.currentThread().getName() + ":" + currentUser.get();
        //设置用户信息到ThreadLocal
        currentUser.set(userId);
        timeCounter.set(System.currentTimeMillis());
        //设置用户信息之后再查询一次ThreadLocal中的用户信息
        String after = Thread.currentThread().getName() + ":" + currentUser.get() + ":" + timeCounter.get();
        //汇总输出两次查询结果
        Map result = new HashMap();
        result.put("before", before);
        result.put("after", after);
        return result;
    }

}
