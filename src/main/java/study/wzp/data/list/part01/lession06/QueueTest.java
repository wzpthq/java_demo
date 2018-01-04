package study.wzp.data.list.part01.lession06;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class QueueTest {

    public static void main(String[]  args) throws InterruptedException {

        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(1);
        // poll 出队列，但是如果队列为空是，不会阻塞，是直接返回null
        System.out.println(queue.poll());
        // queue.take(); 与poll相比，会阻塞
        //queue.offer("1");
        //queue.put("2");
        // offer与put，前者是非阻塞，后者是阻塞
    }
}
