package study.wzp.data.list;

/**
 * 单向链表
 */

public class SinglyLinkedList {

    // 头元素,如果列表为空的时候，那么header.next = null
    private Node header = new Node();

    // 元素个数
    private int size;

    public SinglyLinkedList() {

    }

    /**
     * 表头插入元素, 会导致写入数据与读区顺序相反，插入效率O(1)
     * @param element
     */
    public void addToHeader(Object element) {

        Node node = new Node(element, null);

        if (header != null) {
            node.next = header.next;
            header.next = node;

            size ++;
        }

    }



    /**
     * 表尾插入元素，我们要怎么方尾部插入元素呢？
     * 如果只有头元素指针，那么必须循环列表到最后，确认是最后的元素然后再插入元素,在这种情况下，插入元素的效率是O(n)
     */
    public void addToTail(Object element) {

        Node node = new Node(element, null);

        Node p = header;

        while (p.next != null) {
            p = p.next; // loop
        }

        // loop结束时：p就是最后的元素

        node.next = p.next;
        p.next = node;

        size ++;

    }


    /**
     * 在指定位置插入元素
     * 1、先找到元素；查找元素O(pos)，可以理解为O(n)
     * 2、然后插入元素；
     * @param pos
     * @param element
     */
    public void insert(int pos, Object element) {

        // 判断插入的位置是否合法
        if (pos < 0 || pos > size) {
            throw new IllegalArgumentException("SinglyLinkedList insert element Exception, position illegal");
        }

        Node node = new Node(element, null);

        // 如何插入呢？
        // 需要进行遍历，找到元素，然后进行插入
        Node p = header;

        int index = 0;

        while (p.next != null) {
            if (index == pos) {
                break;
            }
            p = p.next;
            index ++;
        }

        node.next = p.next;
        p.next = node;

        size ++;
    }

    /**
     * 遍历打印
     */
    public void show() {

        Node p = header;

        while (p.next != null) {
            System.out.print(p.next.data + " ");
            p = p.next;
        }

    }

    public int getSize() {
        return this.size;
    }



    /**
     * 元素节点
     */
    class Node {

        // 数据
        private Object data;

        // 指向下一个元素的指针
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



}
