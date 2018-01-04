package study.wzp.data.list.part01.lession07;



/**
 * Future设计模式测试
 * Main Thread
 * Client Thread
 */

public class FutureTest {


    static class FutureData {

        Object data;

        public void setData(Object data) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    FutureData.this.data = data;

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    synchronized (FutureData.this) {
                        FutureData.this.notifyAll();
                    }

                }
            }).start();
        }

        public Object getData() {

            synchronized (this) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return data;
        }

    }


    public static void main(String[] args) {

        FutureData data = new FutureData();
        data.setData("data");

        System.out.println("step1");
        System.out.println("step2");

        System.out.println(data.getData()); // 会阻塞等待



    }

}
