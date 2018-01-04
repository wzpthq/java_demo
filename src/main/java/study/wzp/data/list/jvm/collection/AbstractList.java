package study.wzp.data.list.jvm.collection;

/**
 * 抽象列表
 * @param <T>
 */
public abstract class AbstractList<T> extends AbstractCollection<T> implements List<T> {

    /** 修改次数 */
    protected transient int modCount = 0;

    @Override
    public boolean add(T t) {
        add(size(), t);
        return true;
    }

    @Override
    public ListIterator<T> listIterator() {
        return listIterator(0);
    }

    @Override
    public T remove(int index) {

        if (index < 0 || index >= size()){
            throw new IllegalArgumentException("非法索引位置");
        }

        T t = null;

        ListIterator<T> iterator = listIterator();

        int i = 0;
        while(iterator.hasNext()) {
            if (i == index){
                t = iterator.next();
                iterator.remove();
            }
            i ++;
        }

        return t;
    }

    @Override
    public int indexOf(Object o) {

        ListIterator<T> iterator = listIterator();

        if (null == o) {
            while(iterator.hasNext()) {
                if (iterator.next() == null) {
                    return iterator.nextIndex();
                }

            }
        } else {
            while(iterator.hasNext()) {
                if (iterator.next().equals(o)) {
                    return iterator.nextIndex();
                }
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {

        ListIterator<T> iterator = listIterator(size());

        if (null == o) {
            while(iterator.hasPrevious()) {
                if (iterator.previous() == null) {
                    return iterator.previousIndex();
                }
            }
        } else {
            while(iterator.hasNext()) {
                if (iterator.previous().equals(o)) {
                    return iterator.previousIndex();
                }
            }
        }

        return -1;
    }


    private class Itr implements Iterator<T> {

        /** 迭代器游标 **/
        int cursor = 0;

        /** 最近一次返回的元素索引 **/
        int lastRet = -1;

        /** 初始化迭代器时，设置迭代器与列表状态一致 **/
        int expectedModCount = modCount;

        /**
         * 如果游标指向末尾，那么没有下一个元素
         * @return
         */
        @Override
        public boolean hasNext() {
            return cursor != size();
        }

        /**
         * 取下一个元素，这里为了保护在迭代过程中，输出的结果与预期的结果，做了检测来保证迭代过程
         * @return
         */
        @Override
        public T next() {
            T t = null;
            // #TODO: 核心点，检查迭代器是否被修改
            checkForComodification();

            try {
                t = get(this.cursor);
                lastRet = this.cursor;
                this.cursor ++;
            } catch (IndexOutOfBoundsException e) { // 取出元素越界了
                // TODO: 为啥要再检查一次是否被修改
                checkForComodification();
                throw new NoSuchElementException();
            }

            return t;
        }


        /**
         * 删除lastRet指向的位置元素，因为lastRet指向最近一次迭代的元素
         * 删除操作，需要配合next()方法一起使用，一般是调用一次next后再使用remove
         * @return
         */
        @Override
        public void remove() {

            if (lastRet < 0) { // 无元素
                throw new IllegalStateException("无法删除元素");
            }

            //TODO: 删除时，也需要做一次检查，原因是因为如果不做检查，会导致删除的不是预期的元素
            checkForComodification();

            try {
                // 删除指定个元素
                AbstractList.this.remove(lastRet);

                // 删除后，当前游标需要指向删除的上一个元素
                if (lastRet < cursor) // 正常情况lastRet = cursor - 1
                    cursor --;

                lastRet = -1;

                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException e) {
                // TODO: 这种删除操作，只有出现迭代器发生变化了才会导致IndexOutOfBoundsException
                // 不然，lastRet一定是存在的，也就是可以直接删除的元素
                throw new ConcurrentModificationException();
            }

        }

        private void checkForComodification() {
            if(expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
        }

    }

    /**
     * 列表迭代器，可以控制从哪个元素开始
     */
    private class ListItr extends Itr implements ListIterator<T> {

        ListItr(int index) {
            // TODO: 设置了cursor，就会导致元素的开始位置发生了变化，也就是可以指定迭代的初始位置
            this.cursor = index;
        }

        /**
         * 是否存在前驱元素，如果游标<=0就是没有前驱元素
         * @return
         */
        @Override
        public boolean hasPrevious() {
            return cursor != 0;  //TODO:也可以cursor > 0;
        }

        /**
         * 获取前驱元素
         * @return
         */
        @Override
        public T previous() {

            // 有可能取元素越界
            T t = get(previousIndex());

            return null;
        }

        /**
         * 下一个元素索引,实际上就是cursor
         */
        @Override
        public int nextIndex() {
            return cursor;
        }

        /**
         * 前一个元素索引，就是下一个元素索引-1
         * @return
         */
        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void set(T t) {

        }

        @Override
        public void add(T t) {

        }
    }




}
