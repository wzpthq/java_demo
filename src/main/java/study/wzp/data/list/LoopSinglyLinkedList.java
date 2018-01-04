package study.wzp.data.list;

/**
 * 单向循环链表。和普通的单链表有啥区别呢？
 * 增加一个指针，指向头指针。
 */
public class LoopSinglyLinkedList {

    // 头指针
    private Node head = new Node();

    // 尾指针
    private Node tail = new Node(); // 指向最后一个元素

    private int size;

    public LoopSinglyLinkedList() {
        init();
    }

    private void init() {
        head.next = tail;
        tail.next = head;
    }

    public boolean isEmpty() {
        // head.next==head || tail.next=tail
        return head.next == tail ? true : false;
    }

    /**
     * 插入元素
     * @param pos
     * @param element
     */
    public void insert(int pos, Object element) {

        // 判读插入位置是为合理
        if (pos < 0 || pos > size) throw new IllegalArgumentException("Insert Element Exception.");

        Node node = new Node(element, null);

        int i = 0;
        Node p = head;
        while (p.next != tail) { // 循环
            if (i == pos) break;
            p = p.next;
            i ++;
        }

        node.next = p.next;
        p.next = node;

        size ++;
    }

    /**
     * 删除元素，与单链表没区别
     * @param pos
     */
    public void remove(int pos) {

        // 判断位置是否合法
        if (pos <0 || pos >= size - 1) {
            throw new IllegalArgumentException("Remove Element Exception.");
        }

        // 要删除元素必须找到该元素的前驱元素
        Node p = head;
        for (int i = 0; i < pos; i ++) {
            p = p.next; // 找到前驱元素
        }

        Node currentNode = p.next;
        p.next = currentNode.next;

        size --;

    }

    /**
     * 表头插入元素
     * @param element
     */
    public void addToHead(Object element) {

        Node node = new Node(element, null);

        node.next = head.next;
        head.next = node;

        size ++;
    }

    /**
     * 从表尾部插入元素
     * @param element
     */
    public void addToTail(Object element) {

        Node node = new Node(element, null);

        Node p = head;
        while(p.next != tail) {
            p = p.next;
        }

        node.next = tail;
        p.next = node;

        size  ++;
    }

    public int getSize() {
        return size;
    }

    public Node getHead() {
        return head;
    }

    public Node getTail() {
        return tail;
    }

    public void show() {

        Node p = head;
        while(p.next != tail) {
            p = p.next;
            System.out.print(p.data + " ");
        }
    }


    public static class Node {

        private Object data;

        private Node next;

        public Node() {
        }

        public Node(Object data, Node next) {
            this.data = data;
            this.next = next;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }



    public static void main(String[] args) {

        LoopSinglyLinkedList loopSinglyLinkedList = new LoopSinglyLinkedList();
        loopSinglyLinkedList.addToHead(1);
        loopSinglyLinkedList.addToHead(2);
        loopSinglyLinkedList.addToHead(3);

        loopSinglyLinkedList.show();

        loopSinglyLinkedList.insert(1, 4);

        loopSinglyLinkedList.show();

        System.out.println("============");

        LoopSinglyLinkedList loopSinglyLinkedList1 = new LoopSinglyLinkedList();
        loopSinglyLinkedList1.addToTail(1);
        loopSinglyLinkedList1.addToTail(2);
        loopSinglyLinkedList1.addToTail(3);

        loopSinglyLinkedList1.show();

        loopSinglyLinkedList1.insert(1, 4);

        loopSinglyLinkedList1.show();

        loopSinglyLinkedList1.remove(2);
        System.out.println("============");

        loopSinglyLinkedList1.show();

    }

}
