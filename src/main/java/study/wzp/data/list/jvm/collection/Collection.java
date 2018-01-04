package study.wzp.data.list.jvm.collection;

/**
 * 集合，集合有哪些基本的操作
 * 1,
 * @param <T>
 */
public interface Collection<T> extends Iterable<T>{

    /**
     * 集合大小
     * @return
     */
    public int size();

    /**
     * 是否为空
     * @return
     */
    public boolean isEmpty();

    /**
     * 是否包含某个元素
     * @return
     */
    public boolean contains(Object o);

    /**
     * 添加元素
     * @return
     */
    public boolean add(T t);

    /**
     * 批量添加元素
     * @param sub
     * @return
     */
    public boolean addAll(Collection<? extends T> sub);

    /**
     * 删除元素
     * @param o
     */
    public boolean remove(Object o);

    /**
     * 批量删除元素
     * @param sub
     * @return
     */
    public boolean removeAll(Collection<T> sub);

    /**
     * 集合转换成数组，返回数组
     * @return
     */
    public Object[] toArray(); //TODO: 为何是返回Object而不是T[]

    /**
     * 集合转换到指定数组中
     * @param t
     * @return
     */
    public <E> E[] toArray(E[] t);

    public void clear();

    @Override
    Iterator<T> iterator();



}
