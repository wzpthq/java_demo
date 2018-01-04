package study.wzp.data.list.part01.lession04;

import java.lang.reflect.Array;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * 使用CAS实现Stack
 */

public class CasStack {

    /**
     * 使用数组实现栈
     */
    AtomicReferenceArray<Object> elements;

    /** 数量 */
    AtomicInteger size = new AtomicInteger(0);

    /** 容量 */
    AtomicInteger capacity;

    /** 默认容量 */
    public final static int DEFAULT_CAPACITY = 10;

    public CasStack(int capacity) {

        this.capacity = new AtomicInteger(capacity);

        if (capacity <= 0) {
            elements = new AtomicReferenceArray(DEFAULT_CAPACITY);
        } else {
            elements = new AtomicReferenceArray(capacity);
        }

    }

    /**
     * 入栈，通过CAS保证并发
     * @param o
     */
    public void push(Object o) {

        while(true) {
            if (elements.compareAndSet(size.get(), null, o)){
                break;
            }
        }

        size.incrementAndGet();

    }


    /**
     * 出栈，通过CAS保证并发
     * @return
     */
    public Object pop() {
        Object element = elements.get(size.get() - 1);
        size.decrementAndGet();
        return element;
    }

    public static void main(String[] args) throws InterruptedException {

        Thread[] threads = new Thread[10000];

        final CasStack casStack = new CasStack(100000);

        final AtomicInteger counter = new AtomicInteger(0);

        for (int i = 0; i < threads.length; i ++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10; j ++) {
                        try {
                            casStack.push(this.getClass());
                        } catch (IndexOutOfBoundsException e) {
                            counter.incrementAndGet();
                        }

                    }

                }
            });
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }

        System.out.println(counter.get() + " : " + casStack.size);

    }



}
