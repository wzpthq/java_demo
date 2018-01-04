package study.wzp.data.list.jvm;

public class SyncTest {

    static class Sync {

        public synchronized static void do1() {
            try {
                Thread.sleep(100000);
                System.out.println("=======do1======");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public synchronized static void do2() {
            try {
                Thread.sleep(200000);
                System.out.println("=======do2======");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Sync.do1();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                new Sync().do2();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();


    }
}
