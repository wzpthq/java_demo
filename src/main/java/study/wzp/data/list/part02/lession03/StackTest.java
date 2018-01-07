package study.wzp.data.list.part02.lession03;

import org.junit.Test;

/**
 * 测试设置栈空间
 */

public class StackTest {

    public static void doStack() {
        doStack();
    }

    /**
     * 设置-Xss5m，-Xss10m 看迭代次数
     * 结果，就是设置大的，执行次数多
     */
    public static void main(String[] args) {
        doStack();
    }

}
