package study.wzp.data.list.part01.lession04;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerTest {

    /**
     * 计数器使用原子变量实现
     */
    static class Counter {

        AtomicInteger counter = new AtomicInteger(0);

        public void incr() {
            counter.incrementAndGet();
        }

        public int get() {
            return counter.get();
        }

    }


    static class CounterThread extends Thread{

        Counter counter;

        public CounterThread(Counter counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000; i ++) {
                counter.incr();
            }

        }
    }


    public static void main(String[] args) throws InterruptedException {

        Thread[] threads = new Thread[10];

        Counter counter = new Counter();

        for (int i = 0; i< 10; i ++) {
            threads[i] = new CounterThread(counter);
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }

        System.out.println(counter.get());
    }

}
