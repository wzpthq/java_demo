package study.wzp.data.list;

/**
 * 线性表：就是一组数据，这组数据没个元素都唯一至多有一个前驱元素和一个后继元素（逻辑结构）
 *
 * 线性表是一种逻辑结构，对应物理结果存在两种：
 * 1、顺序存储结构：数组
 * 2、链式存储结构：链表
 * 这个一节我们单独定义数组特征的线性表
 */

/**
 * 顺序存储线性表：有那么基本属性和操作呢：
 * 属性：
 * 1、数据集合；
 * 2、容量；
 * 3、当前数据集合大小；
 *
 * 操作：
 * 1、插入一个元素；
 * 2、删除一个元素；
 * 3、查询一个元素；
 * 4、遍历一个集合；
 *
 * 适合场景：随机查询比较多，不是那么频繁的进行插入和删除操作
 */
public class List {

    /** 数组存储数据 */
    private Object[] elements;

    /** 数组长度 */
    private int size;

    /** 数据容量 */
    private int capacity = 10; // 可以进行动态扩容

    public List() {
        this.elements = new Object[this.capacity];
    }

    public List(int capacity) {
        this.capacity = capacity;
        this.elements = new Object[this.capacity];
    }

    /**
     * 插入一个元素
     * @param  pos 插入的位置
     * @param element 元素
     */
    public void insert(int pos, Object element) {
        // 判断插入的位置是否合理
        if (pos < 0 || pos > this.size)
            throw new IllegalArgumentException("Insert Element Exception, Index Error.");

        // 检查容量
        if (size + 1 > capacity) {
            throw new IllegalArgumentException("Insert Element Exception, Out Of Capacity ");
        }

        /**
         * 分析：我们在插入元素时
         * 如果刚好是从尾部插入，那么算法的执行效率是O(1)，如果是开始位置插入元素那么是O(n)，因此总体的插入元素效率
         * 是O(n)
         */

        // 插入操作时，会导致pos位置之后的元素后移一位
        // 1. 移动pos后面的元素
        for (int i = size -1; i >= pos; i --) {
            elements[i + 1] = elements[i];
        }

        // 2. pos位置插入元素
        elements[pos] = element;

        // 3. size + 1
        size ++;
    }

    public void add(Object element) {
        this.insert(this.size, element);
    }

    /**
     * 删除指定位置的元素
     * @param pos 位置
     */
    public void remove(int pos) {
        // 检查位置是否合法
        if (pos < 0 || pos >= size) {
            throw new IllegalArgumentException("Remove Element Exception, Because Of position error.");
        }

        /**
         * 分析删除元素：实际上的算法执行效率也是O(n)
         * 删除元素实际上是将后面的元素往前移动一个位置
         */
        // 1. 移动元素，在移动的过程中，实际上就是覆盖了原来的元素
        for (int i = pos; i < size - 1; i ++) {
            elements[i] = elements[i + 1];
        }

        // 2. 数据量减少1
        size -- ;

    }




    /**
     * 查询指定位置元素
     *
     * 其算法执行效率是O(1)，为什么呢？我们结合之前学习的汇编内容数据的访问，当我们创建一个数组时，实际上是开辟了一块连续的存储空间，
     * 我们只要知道了开始地址就能直接访问任何位置的元素,假设一个存储单元宽度是s
     * a(n) = a(0) + s * n，所以无论n是多少，都是一次寻址过程，非常快速，因此我们也将这个称之为随机访问。
     * @param pos
     * @return
     */
    public Object get(int pos) {

        if (pos < 0 || pos >= size) {
            throw new IllegalArgumentException("Remove Element Exception, Because Of position error.");
        }

        return elements[pos];
    }

    /**
     * 遍历打印元素
     */
    public void show() {
        for (int i = 0; i < size; i ++) {
            System.out.print(elements[i] + " ");
        }
    }

    public int size() {
        return this.size;
    }
}

