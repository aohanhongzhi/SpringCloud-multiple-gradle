package hxy.dream.common.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author eric
 * @description
 * @date 2021/12/23
 */
public class ThreadPoolExecutorTool {
    /**
     * 阿里巴巴建议自己手动新建线程池，一定要指定最大线程数。
     * 这里的线程池maximumPoolSize依据CPU核心数来确定核心线程数有多少个。一般是核心数+1
     */
    public static ExecutorService POOL = new ThreadPoolExecutor(8, 16,
            15L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(2048), Executors.
            defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
}
