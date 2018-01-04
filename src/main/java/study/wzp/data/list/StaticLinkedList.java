package study.wzp.data.list;

/**
 * 静态链表：数据方式实现的链表
 * 通过游标模拟指针，第一个元素来指定第一个空元素位置（备用链表），最后一个元素当作表头元素，指向第一个插入的元素位置
 *
 * QA列表：
 * 1、第一个元素是指向备用链表的第一个元素位置，那么如果不存在备用链表时，其指向什么 ？？？？？
 * 2、插入/删除/修改元素时，我们是按照数据的下并                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     标位置，还是按照逻辑位置进行操作 ？？？
 * 3、最后一个元素可以当作是链表的表头，指向第一个插入的元素，但是初始化的时候是0，那么我们是不是在插入第一个元素时复制cursor，设置为空时为0；
 *
 * 可以解决没有指针情况下的链表实现，但是缺点依旧是数据的顺序存储问题导致了碎片化数据。
 *
 * 记住一点：数组的第一个元素是存放第一个备用链表元素的索引，最后一个元素可以理解为是链表表头
 *
 * 插入操作步骤：（输入：插入位置，注意这个位置是链表的逻辑顺序，不是数据的下标顺序）
 * 和链表操作差不多，需要移动游标，
 * 1、待插入元素的游标指向上一个元素指向指针，即下一个游标；
 * 2、上一个元素的游标指向待插入元素的位置；
 * 3、第一个元素的游标，指向下一个备用链表的位置（来源于插入之前的游标值）
 *
 * 删除操作：
 * 查找动作基本一致，删除元素也是移动游标
 * 1、上一个元素的游标指向，当前元素的下一个元素；
 * 2、设置第一个元素的备用链表位置为当前删除的元素，删除元素作为第一个备用链表元素位置，并指向删除之前的备用链表第一个元素位置。
 */
public class StaticLinkedList {

    private Node[] nodes;

    private int capacity;

    private int size;


    public StaticLinkedList(int capacity) {
        this.capacity = capacity;
        init();
    }

    // init list
    private void init() {

        nodes = new Node[this.capacity];

        for (int i = 0; i < this.capacity - 1; i ++) {
            nodes[i] = new Node(null, 0);
            nodes[i].cursor = i + 1;
        }

        nodes[capacity - 1] = new Node(null, 0);
        nodes[capacity - 1].cursor = 0;
    }


    /**
     * 指定位置插入元素
     * @param pos
     * @param element
     */
    public void insert(int pos, Object element) {
        // 判断插入位置是否合理
        if (pos < 0 || pos > this.size) {
            throw new IllegalArgumentException("Insert Element Exception.");
        }

        // 获取备用链表位置
        int backupListFirstPosition = nodes[0].cursor;

        // 如果设置可以扩容的链表，不需要考虑这个问题，只需要做check备用链表位置是否正常即可
        if (backupListFirstPosition == 0 || size >= this.capacity - 2)  throw new RuntimeException("链表已满");


        // ================== 开始插入元素 ====================== //
        int index = this.capacity - 1;

        for (int i = 1; i <= pos; i ++) { // 找到pos的前一个元素, 从header开始，循环pos-1次，找到pos的前一个元素的
            index = nodes[index].cursor;
        }

        // 设置元素到备用位置 and 修改游标
        int nextBackupListFirstPos = nodes[backupListFirstPosition].cursor;
        nodes[backupListFirstPosition] = new Node(element, nodes[index].cursor); // 将新插入的元素游标指向，索引出来的上一个元素指向的游标
        nodes[index].cursor = backupListFirstPosition; // 前一个元素，指向备用位置

        // 设置新的备用位置
        nodes[0].cursor = nextBackupListFirstPos;

        size ++;
    }

    /**
     * 删除指定位置的元素
     * @param pos
     */
    public void remove(int pos) {

        // 判断位置是否合理
        if (pos < 0 || pos >= size - 1)  throw new IllegalArgumentException("Remove Element Exception.");

        // 从头元素开始找到指定元素
        int index = this.capacity - 1; // 头元素索引

        for (int i = 1; i <= pos; i++) {
            index = nodes[index].cursor; // 找到删除元素的删一个元素
        }

        int currentIndex = nodes[index].cursor;
        // 将上一个元素的游标指向当前元素下一个元素的位置
        nodes[index].cursor = nodes[currentIndex].cursor;

        // 清楚当前元素的游标,当前元素变成备用链表的元素
        nodes[currentIndex].cursor = nodes[0].cursor; // 设置为备用元素
        nodes[0].cursor = currentIndex; // 将记录备用链表的元素，指向删除的元素位置，删除元素位置的游标指向原来的备用链表第一个元素位置

        size --;

    }

    public int getSize() {
        return size;
    }

    /**
     * 打印链表
     */
    public void show() {

        Node node = nodes[this.capacity - 1];

        int index = 0;
        while (node.cursor != 0) {
            index = node.cursor;
            node = nodes[node.cursor];
            System.out.println("index:" + index  + ", cursor: " + node.cursor + ", data: " + node.element);
        }

    }


    class Node {

        // 数据元素
        private Object element;

        // 游标
        private int cursor;

        public Node() {

        }

        public Node(Object element, int cursor) {
           this.element = element;
           this.cursor = cursor;
        }

        public Object getElement() {
           return element;
        }

        public void setElement(Object element) {
           this.element = element;
        }

        public int getCursor() {
           return cursor;
        }

        public void setCursor(int cursor) {
           this.cursor = cursor;
        }
    }


    public static void main(String[] args) {

        StaticLinkedList staticLinkedList = new StaticLinkedList(10);

        staticLinkedList.insert(0, 0);
        staticLinkedList.insert(1, 2);
        staticLinkedList.insert(2, 3);
        staticLinkedList.insert(3, 4);

        staticLinkedList.show();

        staticLinkedList.insert(1, 1);

        System.out.println("==========Insert =========");

        staticLinkedList.show();

        System.out.println("==========Delete =========");

        staticLinkedList.remove(1);

        staticLinkedList.show();

    }


}
