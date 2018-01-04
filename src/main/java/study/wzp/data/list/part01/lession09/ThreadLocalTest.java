package study.wzp.data.list.part01.lession09;

import org.junit.Test;

import java.lang.reflect.Field;

public class ThreadLocalTest {


    @Test
    public void testThreadLocal() throws InterruptedException {

        ThreadLocal<String> tl = new ThreadLocal<>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Thread t = Thread.currentThread();
                tl.set(Thread.currentThread().getName());

                try {

                    Field field = t.getClass().getDeclaredField("threadLocals");
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    System.out.println("1: " + field.get(t));
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }


            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Thread t = Thread.currentThread();
                tl.set(Thread.currentThread().getName());

                try {
                    Field field = t.getClass().getDeclaredField("threadLocals");
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    System.out.println("2: " + field.get(t));
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        Thread.sleep(1000);


    }

    @Test
    public void testThreadLocal2() {
        ThreadLocal<Object> tl = new ThreadLocal<>();
        tl.set(new byte[1024 * 1024]);
        System.out.println(tl.get());
        tl.set(new byte[1024 * 1024]);
        System.out.println(tl.get());


    }
}
