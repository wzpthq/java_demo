package study.wzp.data.list.part02.lession05;

import org.junit.Test;

/**
 * 准备阶段：
 * 1、为类变量分配内存；
 * 2、为类变量设置初始值或者初始化；
 *
 * 这里主要需要明确的就是static变量、static静态快、final static变量之间的关系引用
 *
 */
public class PreparationTest {


    static class A {

        static int a = 1;

        static {
            System.out.println("Class A, a=" + a);

            b = 3;
            // 不能访问b，但是可以赋值，不然会报错提前引用
            // System.out.println("Class A, b=" + b);
        }

        static int b = 2;

        public final static int c = 3;

    }

    // 测试准备阶段
    @Test
    public void test01() {

        /**
         * 为啥是a=1呢？
         * 在准备实际上a = 0,设置了初始值，a=1是初始化clinit函数执行，在初始化阶段执行的，
         * 原因是putstatic指令在clinit函数内调用。
         *
         */
        assert A.a == 1;

        /**
         * 为啥是b=2呢？
         *
         */
        assert A.b == 2;
    }

    /**
     * 这里不会触发初始化函数操作,在准备阶段，针对final static类变量，实际上就已经赋值完成
     * 并塞入到了常量池中。
     */
    @Test
    public void test02() {
        assert A.c == 3;
    }

}
