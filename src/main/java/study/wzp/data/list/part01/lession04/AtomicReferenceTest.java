package study.wzp.data.list.part01.lession04;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 原子引用类型
 */

public class AtomicReferenceTest {

    static class Counter4Reference {

        AtomicReference<Integer> atomicReference = new AtomicReference<Integer>(0);

        public void incr() {
            while(true) {
                Integer value = atomicReference.get();
                if (atomicReference.compareAndSet(value, value + 1)) { // 一直进行重试，如果不成功的话设置
                    break;
                }
            }
        }

        public int get() {
            return atomicReference.get();
        }
    }

    static class CounterThread extends Thread{

        Counter4Reference counter;

        public CounterThread(Counter4Reference counter) {
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

        Counter4Reference counter = new Counter4Reference();

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
