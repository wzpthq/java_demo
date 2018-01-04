package study.wzp.data.list.part01.lession02;

/**
 * 关于Suspend和Resume方法的使用
 * suspend 挂起
 * resume 恢复
 * 弃用的原因是，如果suspend和resume使用顺序不合理，会导致deadlock
 */
public class SuspendResumeCase {

    private static Object lock = new Object();

    static class SuspendThread extends Thread {

        @Override
        public void run() {
            System.out.println("挂起前");
            this.suspend();
            System.out.println("挂起后");
        }
    }


    static class ResumeThread extends Thread {

    }

    public static void main(String[] args) throws InterruptedException {

        SuspendThread suspendThread = new SuspendThread();
        suspendThread.start();
        suspendThread.resume();
        Thread.sleep(1000);
        //suspendThread.resume();
    }

}
