package study.wzp.data.list;

import org.junit.Test;

public class SinglyLinkedListTest {

    @Test
    public void testAddToHeader() {

        SinglyLinkedList linkedList = new SinglyLinkedList();
        linkedList.addToHeader(1);
        linkedList.addToHeader(2);
        linkedList.addToHeader(3);
        linkedList.addToHeader(4);
        linkedList.addToHeader(5);

        linkedList.show();

        System.out.println("size: " + linkedList.getSize());
    }

    @Test
    public void testAddToTail() {

        SinglyLinkedList linkedList = new SinglyLinkedList();
        linkedList.addToTail(1);
        linkedList.addToTail(2);
        linkedList.addToTail(3);
        linkedList.addToTail(4);
        linkedList.addToTail(5);

        linkedList.show();

        System.out.println("size: " + linkedList.getSize());

    }

    @Test
    public void testInsert() {

        SinglyLinkedList linkedList = new SinglyLinkedList();
        linkedList.addToTail(1);
        linkedList.addToTail(2);
        linkedList.addToTail(3);
        linkedList.addToTail(4);
        linkedList.addToTail(5);
        linkedList.insert(5, 6);
        linkedList.show();

        System.out.println("size: " + linkedList.getSize());
    }

}
