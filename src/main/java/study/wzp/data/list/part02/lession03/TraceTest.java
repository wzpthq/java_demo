package study.wzp.data.list.part02.lession03;

import org.junit.Test;

import java.util.WeakHashMap;

/**
 * Trace跟踪参数测试
 */
public class TraceTest {

    /**
     * 运行参数：-XX:+PrintGC
     */
    @Test
    public void testPrintGC() {
        WeakHashMap<String, Object> map = new WeakHashMap<>();
        map.put("name", "zhiping");
        map.put("age", 23);

        // 强制GC
        System.gc();
    }

    /**
     * 运行参数：-XX:+PrintGCDetails
     */
    @Test
    public void testPrintGCDetails() {
        WeakHashMap<String, Object> map = new WeakHashMap<>();
        map.put("name", "zhiping");
        map.put("age", 23);

        // 强制GC
        System.gc();
    }

    /**
     * 运行参数：-XX:+PrintGCDetails -Xloggc:/Users/zhiping.wangsh/Desktop/gc.log
     */
    @Test
    public void testXloggc() {
        WeakHashMap<String, Object> map = new WeakHashMap<>();
        map.put("name", "zhiping");
        map.put("age", 23);

        // 强制GC
        System.gc();
    }

    /**
     * 运行参数：-XX:+PrintHeapAtGC
     */
    @Test
    public void testPrintHeapAtGC() {
        WeakHashMap<String, Object> map = new WeakHashMap<>();
        map.put("name", "zhiping");
        map.put("age", 23);

        // 强制GC
        System.gc();
    }

    /**
     * 运行参数：-XX:+TraceClassLoading
     */
    @Test
    public void testTraceClassLoading() {
        WeakHashMap<String, Object> map = new WeakHashMap<>();
        map.put("name", "zhiping");
        map.put("age", 23);

        // 强制GC
        System.gc();
    }



}
