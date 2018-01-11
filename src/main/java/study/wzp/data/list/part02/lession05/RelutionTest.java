package study.wzp.data.list.part02.lession05;

import org.junit.Test;

/**
 * 解析阶段：就是将符号引用转换成直接引用
 *
 * 在准备阶段，我们为类变量分配了内存，设置了符号引用，这里需要将符号引用转换成直接引用，那么
 * 1、类和接口的解析
 * 2、字段、方法的解析
 *
 * 会解析访问权限和对应关系如果解析失败，会抛出各种异常，例如：NoSuchFieldError, NoSuchMethodError, 非法访问等异常；
 *
 * 一个case：当一个类他的继承类和实现接口有同一个变量的时候，可能解析会失败；
 *
 */

public class RelutionTest {

    static interface A {
        int a = 0;
    }

    static interface A1 extends A{
        int a = 1;
    }

    static interface B {
        int a = 2;
    }

    static class B1 implements A1{
        public static int a = 3;
    }

    static class Sub extends B1 implements B {
        public static int a = 4;
    }

    static class Sub1 extends B1 implements B {
        public static int b = 5;
    }

    @Test
    public void test0() {
        // 这里是可以执行的，因为Sub本身存在a，可以搜索到
        System.out.println(Sub.a);
        // 这里是无法引用的，因为继承的类和实现的接口都有a，它不确认是引用那一个
        // 说白了，就是没有接口和类的引用顺序
        //System.out.println(Sub1.a); // 编译不通过
    }

}
