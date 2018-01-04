package study.wzp.data.list.part01.lession05;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 信号量类测试
 *
 * 一般可以同来做限流处理，比如：最多10个小线程做某些事情，如果满了，就必须等待。
 *
 * 我们通过计数器例子，来控制自增的速率
 *
 */

public class SemaphoreTest {



    public static void main(String[] args) {

        // 新建一个授权为10个permit的信号量对象
        // 你会发现，每次计数器一批和permit的数量一致，也就是限流的方式。
        // accquire获取，release释放
        Semaphore semaphore = new Semaphore(5);

        // 使用原子变量来做计数器
        final AtomicInteger count = new AtomicInteger(0);

        // 创建一个线程池
        ExecutorService service = Executors.newFixedThreadPool(10);

        for (;;) {
            service.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        Thread.sleep(1000);
                        System.out.println(count.incrementAndGet());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        semaphore.release();
                    }

                }
            });
        }


    }


}
