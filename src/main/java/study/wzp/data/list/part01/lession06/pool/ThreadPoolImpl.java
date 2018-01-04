package study.wzp.data.list.part01.lession06.pool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 线程池实现
 *
 * 1, 提交任务；
 * 2, 创建线程 ；
 * 3, 消费任务；
 * 4, 关闭线程池；
 *
 *
 */
public class ThreadPoolImpl implements ThreadPool {

    // 线程池的数量
    private int threshold;

    // 默认10个线程
    private static final int DEFAULT_THREAD = 10;

    // 是否关闭表示标识
    private volatile boolean isShutdown = false;

    // 工作队列
    private BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>();

    // 工作线程
    private HashSet<Worker> workers = new HashSet<>();

    public ThreadPoolImpl() {
        this(DEFAULT_THREAD);
    }

    public ThreadPoolImpl(int threshold) {
        this.threshold = threshold;
    }


    /**
     * 执行任务
     * 1、如果线程池已经使用完了，那么进入到工作队列；
     * 2、如果没有线程，那么创建线程，并执行；
     * 3、
     * @param task
     */
    @Override
    public void execute(Runnable task) {

        // 线程不足时，创建线程放到池子里面
        if (workers.size() < threshold) {
            Worker worker = new Worker();
            workers.add(worker);
            // 必须启动，因为线程是在不停的工作
            worker.start();
            System.out.println("线程不够，启动线程!");
        }

        // 增加拒绝策略, 实际就是判断队列中容量情况是否可以容纳当前任务
        // 稍后来完善这个部分；

        // 将任务插入到队列
        workQueue.offer(task);
        System.out.println("加入任务:" + task);
    }


    /**
     * 关闭线程池
     */
    @Override
    public void shutdown() {
        // 设置关闭标签
        isShutdown = true;

        Iterator<Worker> iterator = workers.iterator();
        while(iterator.hasNext()) {
            iterator.next().interrupt();
        }
    }

    /**
     * 工作线程
     */
    class Worker extends Thread {

        @Override
        public synchronized void run() {

            while(!isShutdown) {
                // 不停的从队列中获取元素
                try {
                    Runnable task = workQueue.take();
                    task.run();
                } catch (InterruptedException e) {
                    this.interrupt();

                }
            }
        }


    }



    public static void main(String[] args) throws InterruptedException {

        ThreadPool pool = new ThreadPoolImpl(4);
        pool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread() + " : do 1");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        pool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread() + " : do 2");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        pool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread() + " : do 3");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        pool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread() + " : do 4");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        pool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread() + " : do 5");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread.sleep(10000);
        pool.shutdown();


    }
}
