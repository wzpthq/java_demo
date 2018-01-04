package study.wzp.data.list.part02.lession02;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * OutOfMemoryError 测试
 */

public class OOMTest {

    /**
     * 永久代OOM测试
     */
    @Test
    public void testPermOOM() {
        //TODO: 1.8 以后，永久代被移除了，换成了Metaspace，使用本地化内存存放元数据
        //不好测试，因为元空间是用的本地化的内存，其限制使用的就是机器内存；
    }

    /**
     * 测试栈溢出
     * 栈存储的是局部变量表、操作数栈、动态链接、方法出口、附件信息；
     *
     * 场景递归调用，栈帧深度过大导致
     */
    @Test(expected = StackOverflowError.class)
    public void testStackOverflow() {
        StackOverFlowFunction.doInStack();
    }

    static class StackOverFlowFunction  {
        public static void doInStack() {
            doInStack();
        }
    }

    /**
     * 测试正常的OOM
     */

    @Test(expected = OutOfMemoryError.class)
    public void testOOM() {

        List<String> l = new ArrayList<>();

        while(true) {
            l.add("1111111111");
        }

    }


}
