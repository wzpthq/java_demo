package study.wzp.data.list;

import org.junit.Before;
import org.junit.Test;

/**
 * 双向循环链表测试
 */
public class DoubleLoopLinkedListTest {

    private DoubleLooplinkedList list = null;

    @Before
    public void init() {
        list = new DoubleLooplinkedList();
    }

    @Test
    public void testAddFirst() {

        list.addFirst(0);
        list.addFirst(1);
        list.addFirst(2);
        list.addFirst(3);
        list.addFirst(4);

        assert list.size() == 5;

        assert list.get(0).toString().equals("4");
        assert list.get(1).toString().equals("3");
        assert list.get(2).toString().equals("2");
        assert list.get(3).toString().equals("1");
        assert list.get(4).toString().equals("0");

    }

    @Test
    public void testAddLast() {

        list.addLast(5);
        list.addLast(6);
        list.addLast(7);

        assert list.get(0).toString().equals("5");
        assert list.get(1).toString().equals("6");
        assert list.get(2).toString().equals("7");

    }

    @Test
    public void testRemove(){

        list.addLast(5);
        list.addLast(6);
        list.addLast(7);

        assert list.get(1).toString().equals("6");
        list.remove(1); // 删除6

        assert list.size() == 2;
        assert list.get(1).toString().equals("7");
    }

    @Test
    public void testSet() {

        list.addLast(5);

        assert list.get(0).toString().equals("5");

        list.set(0, 6);
        assert list.get(0).toString().equals("6");

    }


}
