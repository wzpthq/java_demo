package study.wzp.data.list;

/**
 * 栈：也是线性表的一种，包含了顺序存储结构和链表存储结构（逻辑存储结构）的内容
 */

public class Stack {

    private Object[] elements;

    private int capacity;

    private final static int DEFAULT_CAPACITY = 10;

    private final static Object[] EMPTY_STACK = {};

    private int top = -1;

    private int size;

    public Stack() {
        this(DEFAULT_CAPACITY);
    }

    public Stack(int capacity) {
        this.capacity = capacity;
        init();
    }

    private void init() {
        this.top = -1;
        this.elements = new Object[this.capacity];
    }

    /**
     * 推入数据
     * @param obj
     */
    public void push(Object obj) {
        if (top >= size) throw new RuntimeException("已满，无法入栈");
        elements[++top] = obj;
        size ++;
    }

    /**
     * 出栈
     */
    public Object pop() {

        if (top < 0) throw new RuntimeException("空栈，无法出栈");

        Object topElement = elements[top];
        elements[top] = null;
        top --;
        size --;

        return topElement;
    }

    /**
     * 查看栈顶元素
     * @return
     */
    public Object peek() {
        if (top < 0) throw new RuntimeException("空栈，无元素");
        return elements[top];
    }

    /**
     * 清空元素,但是没有释放内容
     */
    public void clear() {
        top = -1;
        size  = 0;
    }


    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 查找对象
     * @param o
     * @return
     */
    public int  search(Object o) {
        for(int i = size - 1; i >=0; i --) {
            if (o == elements[i]){
                return i;
            }
        }

        return -1;
    }

    public void show() {

        for (int i = size - 1; i >= 0; i --) {
            System.out.print(elements[i] + " ");
        }

    }

    public int size() {
        return this.size;
    }

}
