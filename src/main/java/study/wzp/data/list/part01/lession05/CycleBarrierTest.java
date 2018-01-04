package study.wzp.data.list.part01.lession05;

import java.util.concurrent.*;

/**
 * 循环栅栏，与CountDownLatch的区别就是可以重复使用，比较适合：
 *
 * 一个任务（做一系列前置并行检查）接着一个任务（做一系列前置检查的任务）；
 *
 * eg：发射火箭
 *
 * 1. 第一个任务：检查燃料；
 *  1.1 检查前舱燃料
 *  1.2 检查中舱燃料；
 *  1.3 检查尾舱燃料
 * 2. 第二个任务：检查电路；
 *  1.1 检查前舱电路
 *  1.2 检查中舱电路；
 *  1.3 检查尾舱电路
 * 3. 第三个任务：检查机器部件；
 *  1.1 检查前舱部件；
 *  1.2 检查中舱部件；
 *  1.3 检查尾舱部件；
 *
 * ================= 最后点火发射 ===================
 *
 */

public class CycleBarrierTest {

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {

        // 创建循环栅栏
        final CyclicBarrier barrier = new CyclicBarrier(3, new Runnable() {
            @Override
            public void run() {
                System.out.println("END");
            }
        });

        ExecutorService service = Executors.newFixedThreadPool(3);

        System.out.println("======Start=====");

        service.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("A:检查前舱燃料");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("B:检查前舱燃料");
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });

        service.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("A:检查中舱燃料");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("B:检查中舱燃料");
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });

        service.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("A:检查后舱燃料");
                try {
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("B:检查后舱燃料");

                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });



        service.shutdown();

        while(!service.isTerminated()) {
            try {
                service.awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



    }
}
