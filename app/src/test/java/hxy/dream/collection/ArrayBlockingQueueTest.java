package hxy.dream.collection;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author eric
 * @program multi-gradle
 * @description 阻塞队列学习
 * @date 2022/3/20
 */
public class ArrayBlockingQueueTest {


    @Test
    public void test1() throws InterruptedException {

        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue<>(2);
        arrayBlockingQueue.put(1);
        arrayBlockingQueue.put(1);
        arrayBlockingQueue.put(1);
        arrayBlockingQueue.put(1);

    }
}
