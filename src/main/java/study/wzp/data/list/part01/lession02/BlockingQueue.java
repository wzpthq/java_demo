package study.wzp.data.list.part01.lession02;

/**
 * 使用wait,notify实现有界阻塞队列
 *
 * 要求：
 * 1，add时，如果队列已经满了，那么需要等待；
 * 2，get时，如果队列为空时，需要等待；
 *
 * add | get都是线程安全的
 *
 * @param <T>
 */
public class BlockingQueue<T> {

    Node<T> head = new Node<>(null, null, null);

    Node<T> tail = new Node<>(null, null, null);

    int capacity;

    int size;

    public BlockingQueue() {
        this(10);
    }

    public BlockingQueue(int capacity) {
        head.next = tail;
        tail.prev = head;
        this.capacity = capacity;
    }

    /**
     * 表尾插入队列
     * @param t
     */
    public synchronized void add(T t) {

        while (size >= capacity) { // 队列已经满了，那么需要等待
            try {
                wait(); // release object monitor lock, notify之后会重新获取锁
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // TODO: 如果被唤醒之后，可能还是队列满
        }

        Node<T> node = new Node<T>(t, null, null);

        node.next = tail;
        tail.prev.next = node;
        node.prev = tail.prev;
        tail.prev = node;

        // 添加完之后，需要发送一个notify消息
        notify(); // 通知等待在this对象的

        size  ++;
    }

    /**
     * 获取元素
     * @return
     */
    public synchronized T get() {

        T t = null;

        while(size <= 0) { // 如果队列是空的
            try {
                wait();
            } catch (InterruptedException e) {
                //TODO 中断消息未处理
                e.printStackTrace();
            }
        }

        Node<T> firstNode = head.next;
        t = firstNode.data;

        head.next = firstNode.next;
        firstNode.next.prev = head;

        notify();

        size -- ;

        return t;
    }

    public int getSize() {
        return size;
    }



    /**
     * 节点
     */
    class Node<T> {

        T data;

        Node<T> prev;

        Node<T> next;

        public Node(T data, Node<T> prev, Node<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public Node<T> getPrev() {
            return prev;
        }

        public void setPrev(Node<T> prev) {
            this.prev = prev;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }
    }


    static class AddThread extends Thread  {

        private BlockingQueue<String> queue;

        private static int i = 1;

        public AddThread(BlockingQueue<String> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while(true) {
                queue.add(Thread.currentThread().getName() + " : " + i);
                System.out.println(Thread.currentThread().getName() + " : " + i);
                i ++;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class GetThread extends Thread  {

        private BlockingQueue<String> queue;

        public GetThread(BlockingQueue<String> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while(true) {
                System.out.println(Thread.currentThread().getName() + " : " + queue.get());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> queue = new BlockingQueue<>(10);

        AddThread addThread = new AddThread(queue);
        GetThread getThread = new GetThread(queue);

        addThread.start();
        getThread.start();

        addThread.join();
        getThread.join();

    }

}
