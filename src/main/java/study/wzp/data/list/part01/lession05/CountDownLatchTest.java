package study.wzp.data.list.part01.lession05;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch 类的使用测试
 *
 * 计数器门闩，什么场景使用呢？
 *
 * 当你之前一个任务时，需要等待一系列的前置操作完成，才能开始执行。
 *
 * 例如：火箭发射，等待：检查燃料、检查机器部件、检查电路等；检查完成之后，才能点火发射；
 *
 */

public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {

        final CountDownLatch latch = new CountDownLatch(3);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("检查燃料开始.....");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
                System.out.println("检查燃料结束.....");
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("检查部件开始.....");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
                System.out.println("检查部件结束.....");
            }
        });


        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("检查电路开始.....");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
                System.out.println("检查电路结束.....");
            }
        });

        t1.start();
        t2.start();
        t3.start();

        latch.await();

        System.out.println("点火发射.....");

    }


}
