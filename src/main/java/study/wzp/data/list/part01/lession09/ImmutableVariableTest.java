package study.wzp.data.list.part01.lession09;

import org.junit.Test;

/**
 * 不变变量
 */

public class ImmutableVariableTest {

    @Test
    public void test01() {
        Integer i1 = 1;
        for(int i = 0; i < 10; i ++) {
            i1 ++;
            // 你会发现，i1每次打印的地址都不知同一个，说明
            // 每次++，实际上是完成了add之后，然后创建一个新的Integer进行赋值；
            System.out.println(System.identityHashCode(i1));
        }
    }

    private static Integer count = 0;

    @Test
    public void test02() throws InterruptedException {

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 1000; i ++) {
                            synchronized (count) {
                                count ++;
                            }
                        }
                    }
                }
        ).start();

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 1000; i ++) {
                            synchronized (count) {
                                count ++;
                            }
                        }
                    }
                }
        ).start();

        Thread.sleep(2000);

        // 你会发现可能不是2000，原因就是test01中的问题，
        // 从表面上看，我们以为是同一个对象，实际上不是同一个对象
        // 因此每次加锁都没有用；
        System.out.println(count);

    }

}
