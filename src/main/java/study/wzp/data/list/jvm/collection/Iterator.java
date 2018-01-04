package study.wzp.data.list.jvm.collection;

/**
 * 迭代器，用于遍历元素
 * @param <T>
 */
public interface Iterator<T> {

    /**
     * 判断是否有下一个元素，遍历时的条件判断
     * @return
     */
    public boolean hasNext();

    /**
     * 取出下一个元素
     * @return
     */
    public T next();

    /**
     * 删除元素
     * @return
     */
    public void remove();



}
