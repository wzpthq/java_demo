package study.wzp.data.list.part01.lession09;

import org.junit.Test;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

public class ReferenceTest {

    @Test
    public void testSoftReference() throws InterruptedException {

        List<Reference> references = new ArrayList<>();
        ReferenceQueue referenceQueue = new ReferenceQueue();

        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while(true) {
                    try {
                        Reference ref = referenceQueue.remove();
                        System.out.println("ref:" + ref + ", get: " + ref.get());
                        i ++;
                        System.out.println("remove num: " + i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        for (int i = 0; i < 100000; i ++) {
            byte[] bytes = new byte[1024 * 1024];
            Reference ref = new SoftReference(bytes, referenceQueue);
            references.add(ref);
            System.gc();

            System.out.println("produce :" + i);
            TimeUnit.MILLISECONDS.sleep(100);

        }
    }

    /**
     * WeakHashMap每次GC时，都会清除掉
     * @throws InterruptedException
     */
    @Test
    public void testWeakReference() throws InterruptedException {

//        List<Reference> references = new ArrayList<>();
//
//        for (int i = 0; i < 10000; i ++) {
//            references.add(new WeakReference(new Object()));
//            System.out.println("i: " + i + ", size:" + references.size());
//            Thread.sleep(100);
//        }

        Map<String, Object> cache = new WeakHashMap<>();

        for (int i = 0; i < 10000; i ++) {
            cache.put(i + "", new byte[1024 * 1024]);
            System.out.println("i: " + i + ", size:" + cache.size());
            Thread.sleep(100);
        }

    }

    /**
     * 虚引用
     *
     */
    @Test
    public void testPhantomReference() {

    }

    /**
     * 主要是为虚拟机提供的，对象被gc前需要执行finalize方法对象机制
     *
     */
    @Test
    public void testFinalReference() {

    }


}
