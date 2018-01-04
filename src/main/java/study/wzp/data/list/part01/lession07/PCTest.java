package study.wzp.data.list.part01.lession07;



import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 生产消费设计模式测试
 *
 * 多消费者、多生产者
 */
public class PCTest {

    static class Producer implements Runnable {

        BlockingQueue<String> queue;

        int start;

        int end;

        public Producer(BlockingQueue<String> queue, int start, int end) {
            this.queue = queue;
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {

            for(int i = start; i <= end; i ++) {
                try {
                    queue.put(i + "");
                    System.out.println("Producer: " + Thread.currentThread() + ": " + i);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    static class Consumer implements Runnable {

        BlockingQueue<String> queue;

        public Consumer(BlockingQueue<String> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {

            while(true) {
                try {
                    String content = queue.take();
                    System.out.println("Consumer:" + Thread.currentThread() + ": " + content);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }

        }
    }

    public static void main(String[] args) {

        ExecutorService service = Executors.newFixedThreadPool(10);

        BlockingQueue<String> queue = new LinkedBlockingDeque<>();

        service.submit(new Producer(queue, 0, 10));
        service.submit(new Producer(queue, 11, 20));
        service.submit(new Producer(queue, 21, 30));

        service.submit(new Consumer(queue));
        service.submit(new Consumer(queue));
        service.submit(new Consumer(queue));
        service.submit(new Consumer(queue));


    }

}
