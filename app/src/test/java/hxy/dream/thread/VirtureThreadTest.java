package hxy.dream.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * @author eric
 * @description
 * @date 2023/9/22
 */
public class VirtureThreadTest {

    private static final Logger log = LoggerFactory.getLogger(VirtureThreadTest.class);


    public static void main(String[] args) {

        var value = "虚拟线程";
        log.info(value);

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 10_000).forEach(i -> {
                executor.submit(() -> {
                    log.info("Thread " + i + "");
                    Thread.sleep(Duration.ofSeconds(1));
                    return i;
                });
            });
        }  // executor.close() is called implicitly, and waits
    }

}
