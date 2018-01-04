package study.wzp.data.list;

/**
 * 双向循环链表
 */
public class DoubleLooplinkedList {

    private Node head;

    private Node tail;

    private int size;


    public DoubleLooplinkedList() {
        init();
    }

    private void init() {
        head = new Node();
        tail = new Node();

        head.next =  tail;
        tail.next = head;

        head.prev = tail;
        tail.prev = head;
    }

    public int size() {
        return this.size;
    }

    /**
     * 插入元素
     * @param pos
     * @param element
     */
    public void insert(int pos, Object element) {

        if (pos < 0 || pos > this.size) throw new IllegalArgumentException("Insert Element Exception.");

        // 找到前驱元素
        Node p = head;

        for (int i = 0; i < pos; i ++) {
            p = p.next;
        }

        Node node = new Node(element, null, null);

        node.next = p.next;
        node.prev = p;
        p.next = node;

        size ++;
    }

    /**
     * 头插入元素
     * @param element
     */
    public void addFirst(Object element) {

        Node node = new Node(element, null, null);

        node.next = head.next;
        node.prev = head;
        head.next = node;

        size ++;
    }

    /**
     * 尾部插入元素
     * @param element
     */
    public void addLast(Object element) {

        Node node = new Node(element, null, null);


        Node tailPrevNode = tail.prev;

        tailPrevNode.next = node;
        node.next = tail;
        node.prev = tailPrevNode;
        tail.prev = node;


        size ++;
    }

    /**
     * 获取元素
     * @param pos
     * @return
     */
    public Object get(int pos) {

         Node p = head;

         for (int i = 0; i <= pos; i ++) {
            p = p.next;
         }

         return p.getData();
    }

    /**
     * 删除元素
     * @param pos
     */
    public void remove(int pos) {

        // 判断位置是否合理
        if (pos < 0 || pos >= this.size) throw new IllegalArgumentException("Remove Element Exception.");

        Node p = head;

        // 找到前驱元素
        for (int i = 0; i < pos; i ++) {
            p = p.next;
        }

        Node currentNode = p.next;

        p.next = currentNode.next;
        currentNode.next.prev = p;

        size --;
    }

    /**
     * 更新元素
     * @param pos
     * @param element
     */
    public void set(int pos, Object element) {

        if (pos < 0 || pos >= size) {
            throw new IllegalArgumentException("Update Node Exception.");
        }

        Node p = head;

        for (int i = 0; i <= pos; i ++) {
            p = p.next;
        }

        p.setData(element);
    }

    /**
     * 打印结果
     */
    public void show() {

        Node p = head;

        while(p.next != tail) {
            p = p.next;
            System.out.print(p.data + " ");
        }

        System.out.println();
    }


    static class Node {

        private Object data;

        private Node prev;

        private Node next;

        public Node() {
        }

        public Node(Object data, Node prev, Node next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public Node getPrev() {
            return prev;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

}
