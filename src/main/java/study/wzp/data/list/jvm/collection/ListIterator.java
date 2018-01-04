package study.wzp.data.list.jvm.collection;

/**
 * 列表迭代器
 * @param <T>
 */
public interface ListIterator<T> extends Iterator<T> {

    /**
     * #TODO: 为啥要这个函数
     * 是否有前一个元素
     * @return
     */
    boolean hasPrevious();

    /**
     * 上一个元素
     * @return
     */
    T previous();

    /**
     * 下一个元素索引
     * @return
     */
    int nextIndex();

    /**
     * 上一个元素索引
     * @return
     */
    int previousIndex();

    /**
     * 更新元素
     * @param t
     */
    void set(T t);

    /**
     * 添加元素
     * @param t
     */
    void add(T t);

}
