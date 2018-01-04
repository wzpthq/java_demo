package study.wzp.data.list.part01.lession02;

/**
 * 线程响应中断的过程
 * wait  --- notify
 * join
 * sleep
 */
public class InterrupterCase {

    private static Object waitLock = new Object();

    static class WaitThread extends Thread {

        @Override
        public void run() {
//            synchronized (waitLock) { // 获取到了object lock
//                try {
//                    waitLock.wait();
//                } catch (InterruptedException e) { // 响应了中断信号之后，会清楚中断表示标示
//                    System.out.println("interrupted: " + isInterrupted());
//                    Thread.currentThread().interrupt();
//                    System.out.println("interrupted: " + isInterrupted());
//                }
//            }

            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) { // 响应了中断信号之后，会清楚中断表示标示
                    System.out.println("interrupted: " + isInterrupted());
                    Thread.currentThread().interrupt();
                    System.out.println("interrupted: " + isInterrupted());
                }
            }

        }
    }

    static class WaitInterruptedThread extends Thread {

        WaitThread thread;

        public WaitInterruptedThread(WaitThread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            thread.interrupt();
        }
    }


    public static void main(String[] args) throws InterruptedException {

        WaitThread waitThread = new WaitThread();
        waitThread.setName("wait-thread");
        waitThread.start();

        Thread.sleep(1000);

        WaitInterruptedThread waitInterruptedThread = new WaitInterruptedThread(waitThread);
        waitInterruptedThread.start();

        waitThread.join();
        waitInterruptedThread.join();

    }


}
