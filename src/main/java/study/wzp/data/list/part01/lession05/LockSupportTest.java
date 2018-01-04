package study.wzp.data.list.part01.lession05;

import java.util.concurrent.locks.LockSupport;

/**
 * 测试LockSupport
 *
 *
 */
public class LockSupportTest {


    static class LockSupportRun implements Runnable {

        @Override
        public void run() {

            System.out.println("======== park ========");

            LockSupport.park(); // 停放,挂起

            System.out.println("======== unpark ========");

        }
    }

    public static void main(String[] args) throws InterruptedException {



        Thread t1 = new Thread(new LockSupportRun());
        t1.start();
        LockSupport.unpark(t1);
        Thread.sleep(1000);
        System.out.println("======== Sleep ========");




    }


}
