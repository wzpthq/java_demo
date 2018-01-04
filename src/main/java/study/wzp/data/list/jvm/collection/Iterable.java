package study.wzp.data.list.jvm.collection;

/**
 * 可迭代的，如果实现了这个接口，那么就可以返回迭代器
 * @param <T>
 */
public interface Iterable<T> {

    /**
     * 获取迭代器
     * @return
     */
    public Iterator<T> iterator();

}
