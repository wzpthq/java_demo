package study.wzp.data.list.part02.lession05;

import org.junit.Test;

/**
 * 初始化阶段, 说白了就是给类变量设置值的阶段，
 *
 * 我们在准备阶段，会将static变量设置初始值，但是赋值会在初始化阶段发生，除了final static变量以外
 * 其他的类变量，都会在这个阶段进行赋值的；
 *
 * 那么我们需要考虑的是static变量、静态块、静态方法他们的赋值顺序，这个demo重点向阐述的点就在这里即可。
 *
 * 我们通过demo要做如何几个case的验证：
 *
 * 1、初始化顺序；
 * 2、初始化函数的执行情况（
 *      1，类的话确保父类clinit先于子类clinit执行；
 *      2，接口的话，于类不一样，只会在使用的时候初始化；
 *      3，clinit只会执行一次，并且是线程安全的；
 * ）
 */
public class InitialTest {

    /**
     * 初始化顺序
     */
    static class InitSort {

        static int a = 1;

        static {
            a = 2;
            System.out.println("a:" + a);

            // 可以赋值
            b = 4;
            // 无法引用
            //System.out.println("b:" + b);
            c = 6;
        }

        static int b = 3;

        static int c = getC();

        public static int getC() {
            return 5;
        }

    }

    /**
     * 验证：初始化顺序
     */
    @Test
    public void testInitSort01() {
        /**
         * 准备阶段: a = 0;
         * 初始化阶段：执行clinit方法，按照申明的顺序
         * a = 1, 先执行；
         * 然后在static静态块中, a =2 后执行，
         * 因此最终就是2
         */
        assert InitSort.a == 2;
        /**
         * 准备阶段：b = 0
         * 初始化阶段：
         * static 静态块先执行，因此b4，然后b=3，因此最终=3，但是static块中不能引用b，否则
         * 会出现提前引用错误；
         */
        assert InitSort.b == 3;
        /**
         * 准备阶段：c=0；
         * 初始化阶段：
         * static静态块先执行因此c=6，同样不能被引用，然后c=getC()执行静态函数，赋值为5
         */
        assert InitSort.c == 5;

    }


    static class InitSortSub extends InitSort {
        static int d = a;
        static {
            System.out.println("d: before: " + d);
            d = 7;
            System.out.println("d: after: " + d);
        }
    }

    /**
     * 验证继承关系的初始化顺序
     */
    @Test
    public void testInitSort02() {
        /**
         * 准备阶段：d = 0
         * 初始化阶段：
         * d = a, 表示引用了InitSort的a，会触发父类的clinit函数，因此会执行InitSort中的所有初始化操作
         * 同时也会触发InitSortSub执行clinit
         */
        assert InitSortSub.d == 7;
    }


    static class InitSortSub1 extends  InitSort {
        static int e = 2;
    }

    /**
     * 验证继承关系的clinit无关联情况
     */
    @Test
    public void testInitSort03() {
        /**
         * e=2没啥问题，主要需要关注的是是否会因此InitSort的初始化操作，
         * 我们之前说过，子类的初始化，会先确保父类的初始化操作；因此
         * 这里会触发InitSort的初始化操作
         */
        assert InitSortSub1.e == 2;
    }

    static interface InitSort1 {
        int f = 10;
    }

    static class InitSort1Sub extends InitSortSub1 implements InitSort1 {
        static int h = 11;
    }



    @Test
    public void testInitSort04() {
        /**
         * 会触发InitSortSub1和InitSort的初始化操作，但是实际上不会触发InitSort1接口的Clinit函数
         * 因为接口的初始化和类的有点区别，就是使用到了才会执行，否则不会。不过这里没办法看出来
         */
        assert InitSort1Sub.h == 11;
    }

    /**
     * 验证继承结构的初始化
     */
    static class Animal {
        static int age = 0;
        static {
            System.out.println("Animal init");
        }
    }

    static class Person extends Animal{
        static int age = 1;
        static {
            System.out.println("Person init");
        }
    }

    static class Man extends Person {

        public final static int a = 1;

        static int b = 2;

        static {
            System.out.println("Man init");
        }
    }

    /**
     * 目的：测试类继承的静态初始化情况
     */
    @Test
    public void testExtends() {
        /**
         * Man不会初始化，也就是static块不会执行，因为没有引用到Man类的字段
         * 因此会往上找，找到了Person会初始化，然后触发父类Animal的初始化；
         */
        assert Man.age == 1;

        System.out.println("=============");

        /**
         * 调用final static 变量不会触发初始化
         */
        assert Man.a == 1;

        System.out.println("=============");

        /**
         * 引用了类的变量，会触发初始化
         */
        assert Man.b == 2;
    }


    /**
     * 测试clinit是线程安全的，并且有且只会初始化一次
     */
    static class Car {
        static {
            // if true 必须加上，不然无法通过编译，因为static不能写无法初始化完成的代码
            if(true) {
                System.out.println("Car Init: " + Thread.currentThread());
                while (true) {

                }
            }

        }
    }

    /**
     * 你会发现Car Init只会执行一次，并且线程会出现阻塞，说明：
     * 说明只有一个线程进入了clinit方法
     *
     * 说白了，就是当第一次触发初始化时，就会导致clinit执行，如果执行了，实际上会标记成已初始化，
     * 下次再触发的时候，先会做检查，然后再确认；并且保证clinit是线程安全的，就没有其他的问题。
     *
     * @throws InterruptedException
     */
    @Test
    public void testSafeInit() throws InterruptedException {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread() + " start");
                Car car = new Car();
                System.out.println(Thread.currentThread() + " end");
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }




}
