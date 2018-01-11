package study.wzp.data.list.part02.lession04;

/**
 * CMS（Concurrent Mark Sweep）回收器（并发标记清除回收器）
 *
 */
public class CMSTest {

    /**
     * 启动：-XX:+UseConcMarkSweepGC，这是一个老年代的回收器
     * 会经过如下几个步骤：
     * 1、初始化标记(cms-initial-mark): 标记与根对象有直接引用的对象（会阻塞，但是很快）；
     * 2、并发标记（cms-concurrent-mark）: 和用户线程一起运行，标记所有对象；
     * 3、重新标记（cms-remark）: 和用户线程一起，因为和用户线程一起运行，因此会不断产生新的对象，以及标记发生变化，因此会阻塞；
     * 4、并发清除（cms-clear）: 和用户线程一起，基于标记结果，并发的清除；
     *
     * 缺点：由于和用户线程一起执行，会占用CPU资源，降低应用程序的吞吐量；同时存在堆无法分配的情况，因此需要有降级方案，
     *       一般降级为串行回收器；
     * 优点：尽可能的降低了系统停顿；
     *
     [GC (Allocation Failure) [ParNew: 1872K->320K(3072K), 0.0017060 secs][CMS: 156K->466K(2752K), 0.0024844 secs] 1872K->466K(5824K), [Metaspace: 3282K->3282K(1056768K)], 0.0042521 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
     Heap
     par new generation   total 3072K, used 27K [0x00000007bf600000, 0x00000007bf950000, 0x00000007bf950000)
     eden space 2752K,   1% used [0x00000007bf600000, 0x00000007bf606f88, 0x00000007bf8b0000)
     from space 320K,   0% used [0x00000007bf900000, 0x00000007bf900000, 0x00000007bf950000)
     to   space 320K,   0% used [0x00000007bf8b0000, 0x00000007bf8b0000, 0x00000007bf900000)
     concurrent mark-sweep generation total 6848K, used 5586K [0x00000007bf950000, 0x00000007c0000000, 0x00000007c0000000)
     Metaspace       used 3289K, capacity 4496K, committed 4864K, reserved 1056768K
     class space    used 363K, capacity 388K, committed 512K, reserved 1048576K
     *
     */
    public static void main(String[] args) {
        byte[] buffer = new byte[5 * 1024 * 1024];
    }

}
