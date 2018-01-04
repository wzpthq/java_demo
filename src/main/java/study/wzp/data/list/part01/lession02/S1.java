package study.wzp.data.list.part01.lession02;

/**
 * 题目：有三个线程T1，T2，T3，如何保证按照T1,T2,T3的顺序执行
 */

public class S1 {

    static class TThread extends Thread {

        private Thread firstThread;

        public TThread(Thread firstThread, String name) {
            this.firstThread = firstThread;
            super.setName(name);
        }

        @Override
        public void run() {
            if (firstThread != null){
                try {

                    /**
                     * join 等待线程执行完成，实际的原理是
                     * while(isAlive()) {
                     *     wait(0);
                     * }
                     *
                     * 为啥要加上这一句呢，原因是如果线程还未start，那么join就相当于没有执行，也就会出现线程执行顺序不是预期的结果。
                     */
                    while(true) {
                        if (firstThread.isAlive()) {
                            break;
                        }
                    }

                    firstThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(this.getName());
        }
    }


    public static void main(String[] args) throws InterruptedException {

        TThread t1 = new TThread(null, "t1");
        TThread t2 = new TThread(t1, "t2");
        TThread t3 = new TThread(t2, "t3");

        t3.start();
        t2.start();
        t1.start();


        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
