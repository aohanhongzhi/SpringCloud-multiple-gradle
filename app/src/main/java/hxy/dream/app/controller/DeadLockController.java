package hxy.dream.app.controller;

import hxy.dream.entity.vo.BaseResponseVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author eric
 * @program multi-gradle
 * @description 死锁代码
 * @date 2022/1/25
 */
@RestController
@RequestMapping("/deadlock")
public class DeadLockController {
    Object lock = new Object();
    Object lock2 = new Object();

    @RequestMapping("test")
    public BaseResponseVO dead() {
        new Thread(() -> {
            synchronized (lock) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2) {
                    System.out.println("thread1 over");
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (lock2) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock) {
                    System.out.println("thread2 over");
                }
            }
        }).start();
        return BaseResponseVO.success("死锁测试");
    }
}
