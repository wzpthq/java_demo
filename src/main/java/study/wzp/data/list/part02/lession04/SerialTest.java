package study.wzp.data.list.part02.lession04;

/**
 * 串行回收
 */
public class SerialTest {

    /**
     * 运行参数：-XX:+UseSerialGC -Xmx10m -Xmx5m -XX:+PrintGCDetails
     * 使用串行回收器进行垃圾回收，其原理是单独使用一个GC线程来处理垃圾回收
     * 1、新生代：使用复制算法；
     * 2、老年代：使用标记-压缩算法；
     * GC时会出现阻塞。
     * GC日志，会通过DefNew来表示标识，这是串行回收算法
     *
     [GC (Allocation Failure) [DefNew: 1664K->192K(1856K), 0.0033104 secs][Tenured: 258K->449K(4096K), 0.0030029 secs] 1664K->449K(5952K), [Metaspace: 3145K->3145K(1056768K)], 0.0063608 secs] [Times: user=0.01 sys=0.00, real=0.01 secs]
     Heap
     def new generation   total 1920K, used 90K [0x00000007bf600000, 0x00000007bf810000, 0x00000007bf950000)
     eden space 1728K,   5% used [0x00000007bf600000, 0x00000007bf616b70, 0x00000007bf7b0000)
     from space 192K,   0% used [0x00000007bf7b0000, 0x00000007bf7b0000, 0x00000007bf7e0000)
     to   space 192K,   0% used [0x00000007bf7e0000, 0x00000007bf7e0000, 0x00000007bf810000)
     tenured generation   total 6848K, used 5569K [0x00000007bf950000, 0x00000007c0000000, 0x00000007c0000000)
     the space 6848K,  81% used [0x00000007bf950000, 0x00000007bfec0790, 0x00000007bfec0800, 0x00000007c0000000)
     Metaspace       used 3247K, capacity 4496K, committed 4864K, reserved 1056768K
     class space    used 357K, capacity 388K, committed 512K, reserved 1048576K
     */
    public static void main(String[] args) {
        byte[] buffer = new byte[5 * 1024 * 1024];
    }

}
