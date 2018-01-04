package study.wzp.data.list;

import org.junit.Test;

public class StackTest {

    @Test
    public void testPush() {

        Stack stack = new Stack(5);
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        stack.show();
    }

    @Test(expected = RuntimeException.class)
    public void testPushException() {
        Stack stack = new Stack(5);
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        stack.push(6);
        stack.show();
    }

    @Test
    public void testPop() {
        Stack stack = new Stack(5);
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);

        assert stack.pop().toString().equals("5");
        assert stack.pop().toString().equals("4");
        assert stack.pop().toString().equals("3");
        assert stack.pop().toString().equals("2");
        assert stack.pop().toString().equals("1");
    }

    @Test(expected = RuntimeException.class)
    public void testPopException() {
        Stack stack = new Stack(0);
        stack.pop();
    }

}
