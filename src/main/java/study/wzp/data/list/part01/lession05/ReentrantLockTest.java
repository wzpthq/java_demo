package study.wzp.data.list.part01.lession05;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁的测试，测试重入情况
 */

public class ReentrantLockTest {

    static class ReentrantLockRun {

        ReentrantLock lock = new ReentrantLock();

        public void s1() {
            try{
                lock.lock();
                System.out.println("=====S1=====: + " +lock.getHoldCount());
                // 如果不是重入锁，那么按理，这是会出现阻塞
                s2();
            } finally {
                if (lock.isHeldByCurrentThread()){
                    lock.unlock();
                    System.out.println("S1 UNlock: " + lock.getHoldCount());
                }
            }
        }

        public void s2() {
            try{
                lock.lock();

                System.out.println("=====S2=====:" + lock.getHoldCount());

            } finally {
                if (lock.isHeldByCurrentThread()){
                    lock.unlock();
                    System.out.println("S2 UNlock: " + lock.getHoldCount());
                }
            }
        }

    }


    public static void main(String[] args) {

        ReentrantLockRun run = new ReentrantLockRun();
        run.s1();

    }

}
