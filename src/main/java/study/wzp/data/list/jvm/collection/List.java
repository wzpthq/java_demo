package study.wzp.data.list.jvm.collection;

/**
 * 列表
 * @param <T>
 */
public interface List<T> extends Collection<T>{

    /**
     * 获取制定位置元素
     * @param index
     * @return
     */
    T get(int index);

    /**
     * 设置指定位置元素，更新操作，返回设置之前的元素
     * @param index
     * @param element
     */
    T set(int index, T element);

    /**
     * 添加元素
     * @param t
     * @return
     */
    boolean add(T t);

    /**
     * 添加元素
     * @param index
     * @param element
     */
    void add(int index, T element);

    /**
     * 删除指定位置的元素
     * @param index
     * @return
     */
    T remove(int index);


    /**
     * 从左到右查询第一个相等元素的位置
     * @param o
     * @return
     */
    int indexOf(Object o);

    /**
     * 与indexOf查询顺序相反
     * @param o
     * @return
     */
    int lastIndexOf(Object o);


    /**
     * 获取列表迭代器
     * @return
     */
    ListIterator<T> listIterator();

    /**
     * 获取列表迭代器，指定位置开始
     * @param index
     * @return
     */
    ListIterator<T> listIterator(int index);

    /**
     * 子列表
     * @param fromIndex
     * @param toIndex
     * @return
     */
    List<T> subList(int fromIndex, int toIndex);

}
