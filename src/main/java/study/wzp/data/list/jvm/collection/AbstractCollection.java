package study.wzp.data.list.jvm.collection;

import javax.naming.OperationNotSupportedException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 集合的抽象类
 * @param <T>
 */
public abstract class AbstractCollection<T> implements Collection<T> {


    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {

        Iterator<T> iterator = iterator();

        if (null == o) {
            while(iterator.hasNext()) {
                if (null == iterator.next()) return true;
            }
        } else {
            while(iterator.hasNext()) {
                if (iterator.next().equals(o)) return true;
            }
        }

        return false;
    }

    @Override
    public boolean remove(Object o) {

        Iterator<T> iterator = iterator();

        if (null == o) {
            while(iterator.hasNext()) {
                if (iterator.next() == null) {
                    iterator.remove();
                    return true;
                }
            }
        } else {
            while(iterator.hasNext()) {
                if (iterator.next().equals(o)) {
                    iterator.remove();
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean removeAll(Collection<T> sub) {
        while(sub.iterator().hasNext()) {
            this.remove(sub.iterator().next());
            return true;
        }

        return false;
    }

    /**
     * 转换数组的时候，需要考虑集合可能会增加或者删除元素
     * 因此可能导致转换过程中，实际元素变少或者变多
     * @return
     */
    @Override
    public Object[] toArray() {

        Object[] oArray = new Object[size()];

        Iterator<T> iterator = iterator();

        // 遍历赋值元素
        for (int i = 0; i < oArray.length; i ++) {

            if (!iterator.hasNext()) {  // 如果实际元素数量比预期的变少了，那么返回数据
                return Arrays.copyOf(oArray, i); // 将前i个元素
            }

            oArray[i] = iterator.next();
        }

        // 如果实际元素比预期的变多了，那么需要将数组扩容，然后将数据填充进去
        return iterator.hasNext() ? finishToArray(oArray, iterator) : oArray;
    }

    /**
     * 将集合数据转换到外部指定的类型数据组中
     * 1、如果外部数组的容量足够，就直接设置到外部数据并返回；
     * 2、如果外部数组的容量不够，那么会经过数据扩容，返回转换后的数据；
     * @param t
     * @param <E>
     * @return
     */
    @Override
    public <E> E[] toArray(E[] t) {

        // 需要定义泛型数据，直接按照数据的定义方式无法定义，需要通过反射来定义
        // #TODO: E[] e = new E[size()];
        // 如果外部传入的数组大小大于当前数据的容量，那么直接使用外部数组，否则通过java.lang.reflect.Array反射类来创建泛型数组
        E[] e = t.length >= size() ? t : (E[])Array.newInstance(t.getClass().getComponentType(), size());

        Iterator<T> iterator = iterator();

        for (int i = 0; i < e.length; i ++ ) {
            if (!iterator.hasNext()){ // 元素比预期的少
                if (t == e) { // 数组是空的
                    e[i] = null;
                } else if (t.length < i) { // 如果外部数组的容量，小于当前元素容量，那么需要复制截断
                    return Arrays.copyOf(e, i);
                } else { // 外部数组大于当前元素，
                    System.arraycopy(e, 0, t, 0, i); // 将e中元素复制到t中
                    if (t.length > i) {
                        t[i] = null; // 多出的元素，null
                    }
                }
                return t;
            }

            e[i] = (E)iterator.next();
        }

        // 如果元素变多
        return iterator.hasNext() ? finishToArray(e, iterator) : e;
    }

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8; // ？？？？ 为啥是减8，TODO：稍后关注了解

    /**
     * 填充数组完成
     * 这里需要考虑如何扩容，最后的大小=当前数据容量 + 待扩容的容量
     * @param e 待填充的数组
     * @param it 迭代器，用于取出数据
     * @param <E>
     * @return
     */
    private <E> E[] finishToArray(E[] e, Iterator<?> it) {

        int i = e.length;

        while(it.hasNext()) {

            int capacity = e.length;

            if (i == capacity) { // 需要扩容
                int newCapacity = capacity + capacity >> 1 + 1; // 扩容算法：就是原始体量一半的速度增长

                // 判断增长后容量是否合法
                if (newCapacity > MAX_ARRAY_SIZE){  // 已经超出了最大的数组范围
                    newCapacity = hugeCapacity(capacity + 1); // 在计算得到扩容后的容量后，如果比最大容量还大，那么不能直接扩容，只增加1个容量进行尝试，是否可以增长
                }
                e = Arrays.copyOf(e, newCapacity);
            }

            e[i++] = (E)it.next();

        }

        // 如果i == e.length，那么表示数据没有空余位置，全部满了，可以直接返回，否则需要剔除多余的元素，需要进行数据赋值
        return i == e.length ? e : Arrays.copyOf(e, i);
    }


    /**
     * 大容量检查
     * @param capacity
     * @return
     */
    private int hugeCapacity(int capacity) {

        if (capacity < 0){ // 为负数，说明整数已经溢出了，超出了最大的容量范围
            throw new OutOfMemoryError("已超出最大的容量");
        }

        return capacity > MAX_ARRAY_SIZE ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
    }


    @Override
    public boolean addAll(Collection<? extends T> sub) {
        boolean modified = false;
        Iterator<? extends T> iterator = sub.iterator();
        // #TODO:为啥不能使用for循环遍历，涉及到for-each问题，稍后了解

        while(iterator.hasNext()) {
            if(add(iterator.next())){
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public void clear() {
        Iterator<T> iterator = iterator();
        while(iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
    }
}
