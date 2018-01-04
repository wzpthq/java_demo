package study.wzp.data.list.part02.lession02;

import org.junit.Test;

/**
 * String的使用情况
 *
 * 注意一点：
 * 1、JDK 1.6 常量池是在方法区，非堆区
 * 2、JDK 1.7 及其以上，常量池在堆区；
 * intern设计目的：重用String对象
 */

public class StringTest {

    // 测试intern的引用
    @Test
    public void test01() {

        // s1 指向的是常量池
        String s1 = "1111"; // 会在常量池中创建一个1111字符串
        // s2 指向的是堆的对象实例
        String s2 = new String("1111");

        assert s1 != s2;
        // s2.intern，
        assert s1 == s2.intern();  // s2.intern返回常量池的字符串

    }

    @Test
    public void test02() {

        // 在常量池中创建了一个"1"字符串
        String s1 = new String("1");

        // 指向常量池
        String s2 = "1";

        assert s1 != s2;
        assert s1.intern() == s2;
        assert s1.intern() != s1;

    }

    //???????????
    //TODO: 应该执行结果是s3=s4，这个执行结果才是JDK8的预期执行结果
    @Test
    public void test03() {

        String s3 = new String("1") + new String("1");
        s3.intern();
        String s4 = "11";

        System.out.println(System.identityHashCode(s3.intern()));
        System.out.println(System.identityHashCode(s4));

        assert s3.intern() == s4;
    }

}
