package study.wzp.data.list.part01.lession05;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁实现
 *
 * 读-读（不阻塞）
 * 读-写（阻塞）
 * 写-写（阻塞）
 *
 */

public class ReadWriteLockTest {

    static class ReaderWriteRun {

        static ReadWriteLock lock = new ReentrantReadWriteLock();
        static ReentrantReadWriteLock.ReadLock rLock = (ReentrantReadWriteLock.ReadLock) lock.readLock();
        static ReentrantReadWriteLock.WriteLock wLock = (ReentrantReadWriteLock.WriteLock) lock.writeLock();

        public void r1() {
            try {
                rLock.lock();

                Thread.sleep(20000);

                System.out.println("R1:" + Thread.currentThread());
                // 测试重入
                r2();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                rLock.unlock();
            }

        }

        public void r2() {
            try {
                rLock.lock();
                System.out.println("R2:" + Thread.currentThread());
            } finally {
                rLock.unlock();
            }

        }


        public void w1() {
            try {
                wLock.lock();

                Thread.sleep(20000);

                System.out.println("W1:" + Thread.currentThread());
                // 测试重入
                r2();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                rLock.unlock();
            }

        }

        public void w2() {
            try {
                wLock.lock();
                w1();
                System.out.println("W2:" + Thread.currentThread());
            } finally {
                wLock.unlock();
            }

        }

    }

    public static void main(String[] args) throws InterruptedException {

        final ReaderWriteRun run = new ReaderWriteRun();

        new Thread(new Runnable() {
            @Override
            public void run() {
                run.r1();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                run.r1();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                run.w1();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                run.w2();
            }
        }).start();

        Thread.sleep(20000);

    }

}
