package study.wzp.data.list;

/**
 * 队列，也是线性表的一种，和栈一样，也存在顺序存储结构和逻辑存储结构；
 * 但是一般我们建议使用逻辑存储结构，原因是算法效率问题；
 *
 * 队列是FIFO（First IN First Out），一头入队列，另一头出队列。
 *
 * 对头：出队列；
 * 对尾：入队列；
 *
 * 1、顺序存储结构：入队列的算法效率是O(1),但是出队列的算法效率却是O(n)
 * 2、逻辑存储结构：链表的实现（双向），入队列和出队列的算法效率都是O(1)，因此通常使用链表方式来实现
 *
 * 这里的队列只是简单的实现，并没有支持队列任务模式的操作
 */

public class LinkedQueue<T> {

    // 头节点
    private Node header = new Node(null, null, null);

    // 尾节点
    private Node tail =  new Node(null, null, null);

    private int size;

    public LinkedQueue() {
        init();
    }

    /**
     * 不需要建立环，建立双向就可以
     */
    private void init() {
        header.next = tail;
        tail.prev = header;
    }

    public int size() {
        return this.size;
    }


    // 入队列
    public void enqueue(T t) {
        Node node = new Node(t, null, null);

        Node prevNode = tail.prev;

        node.prev = prevNode;
        node.next = tail;

        prevNode.next = node;
        tail.prev = node;

        size ++;
    }

    // 出队列
    public T dequeue() {

        if (isEmpty()) {
            return null;
        }

        Node<T> currentNode = header.next;

        header.next =  header.next.next;
        tail.prev = tail.prev.prev;

        size --;

        return currentNode.data;
    }


    public boolean isEmpty() {
        return this.size == 0;
    }

    public void show() {

        Node p = header;

        while(p.next != tail) {
            p = p.next;
            System.out.println(p.data + " ");
        }

    }


    // 链表节点
    private class Node<T> {

        private T data; // 元素

        private Node prev; // 上一个节点

        private Node next; // 下一个节点

        public Node(T data, Node prev, Node next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }


    public static void main(String[] args) {
        LinkedQueue<String> q = new LinkedQueue<String>();
        q.enqueue("wangzp1");
        q.enqueue("wangzp2");
        q.enqueue("wangzp3");

        q.show();

        System.out.println(q.dequeue());
        System.out.println(q.dequeue());
        System.out.println(q.dequeue());

        System.out.print(q.isEmpty());

    }

}
