package study.wzp.data.list.part01.lession03;

import org.omg.PortableServer.THREAD_POLICY_ID;

/**
 * 线程可见性问题测试
 */
public class VisiableTest {

    static class VisiableThread extends Thread {

        boolean stop = false;

        static int i = 0;

        @Override
        public void run() {
            while(!stop) {
                i++;
            }
            System.out.println("game over! : " + i);
        }

        public void stopIt() {
            this.stop = true;
        }
    }


    public static void main(String[] args) throws InterruptedException {

        VisiableThread thread = new VisiableThread();
        thread.start();

        Thread.sleep(1000);

        thread.stopIt();

        thread.join();
    }

}
