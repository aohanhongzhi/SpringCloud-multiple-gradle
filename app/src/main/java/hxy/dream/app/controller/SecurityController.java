package hxy.dream.app.controller;

import hxy.dream.entity.vo.BaseResponseVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author eric
 * @program eric-dream
 * @description 线程安全
 * @date 2021/8/24
 * @link https://mp.weixin.qq.com/s/WLCCoC6c8TsME4cKxD6G4w
 *
 * https://www.jianshu.com/p/6fc3bba12f38
 *
 * https://blog.csdn.net/qq_28393323/article/details/81003964
 *
 *
 */
@RestController
public class SecurityController {

    private int unsecurityNum = 0;
    private AtomicInteger atomicIntegerNum = new AtomicInteger(0);
    private final ThreadLocal<AtomicInteger> atomicIntegerThreadLocal =
            new ThreadLocal<AtomicInteger>() {
                @Override
                protected AtomicInteger initialValue() {
                    System.out.println("初始值"+atomicIntegerNum);
                    return atomicIntegerNum;
                }
            };

    private int num = 0;
    private final ThreadLocal<Integer> uniqueNum =
            new ThreadLocal<Integer>() {
                @Override
                protected Integer initialValue() {
                    System.out.println("初始值num"+num);
                    return num;
                }
            };

    @GetMapping("/security")
    public BaseResponseVO security() {
        AtomicInteger num = atomicIntegerThreadLocal.get();
        num.incrementAndGet();
        atomicIntegerThreadLocal.set(num);
        final AtomicInteger x = atomicIntegerThreadLocal.get();
        System.out.println(x);
        return BaseResponseVO.success(x);
    }

    @GetMapping("/unsecurity")
    public BaseResponseVO unsecurity() {

        return BaseResponseVO.success(unsecurityNum++);
    }

    @GetMapping("/addNum")
    public BaseResponseVO addNum() {
        int unum = uniqueNum.get();
        uniqueNum.set(++unum);
        final Integer x = uniqueNum.get();
        System.out.println(x);
        return BaseResponseVO.success(Thread.currentThread().getName(), x);
    }

}
