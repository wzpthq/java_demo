package study.wzp.data.list.part01.lession04;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 特殊字段的使用
 */

public class AtomicIntegerFieldUpdaterTest {

    static class Counter {

        // 必须是volatile类型
        volatile int count;

        AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = null;

        {
            atomicIntegerFieldUpdater  = AtomicIntegerFieldUpdater.newUpdater(Counter.class,"count");

        }

        public void incr() {
            atomicIntegerFieldUpdater.incrementAndGet(this);
        }

        public int get() {
            return atomicIntegerFieldUpdater.get(this);
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
        Thread[] threads = new Thread[100];

        Counter counter = new Counter();

        for (int i = 0; i< 100; i ++) {
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
