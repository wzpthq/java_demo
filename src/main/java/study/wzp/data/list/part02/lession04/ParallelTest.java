package study.wzp.data.list.part02.lession04;

import org.junit.Test;

/**
 * 并行回收器进行回收测试
 */
public class ParallelTest {

    /**
     * 并行回收器
     * -XX:+UseParNewGC 启用新生代并行回收器
     * 新生代：使用并行回收策略，复制算法；其实就是多个线程进行清除
     * 老年代：使用串行回收策略，标记-压缩算法；
     *
     [GC (Allocation Failure) [ParNew: 1653K->191K(1856K), 0.0029917 secs] 1653K->433K(5952K), 0.0030210 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] [GC (Allocation Failure) [ParNew: 1842K->192K(1856K), 0.0021161 secs] 2085K->766K(5952K), 0.0021345 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
     [GC (Allocation Failure) [ParNew: 1856K->191K(1856K), 0.0008688 secs] 2430K->1054K(5952K), 0.0008932 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] [GC (Allocation Failure) [ParNew: 1311K->165K(1856K), 0.0005302 secs][Tenured: 964K->955K(4096K), 0.0032252 secs] 2174K->955K(5952K), [Metaspace: 4607K->4607K(1056768K)], 0.0037949 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
     Heap
     par new generation   total 1920K, used 66K [0x00000007bf600000, 0x00000007bf810000, 0x00000007bf950000)
     eden space 1728K,   3% used [0x00000007bf600000, 0x00000007bf610838, 0x00000007bf7b0000)
     from space 192K,   0% used [0x00000007bf7b0000, 0x00000007bf7b0000, 0x00000007bf7e0000)
     to   space 192K,   0% used [0x00000007bf7e0000, 0x00000007bf7e0000, 0x00000007bf810000)
     tenured generation   total 6848K, used 6075K [0x00000007bf950000, 0x00000007c0000000, 0x00000007c0000000)
     the space 6848K,  88% used [0x00000007bf950000, 0x00000007bff3ec68, 0x00000007bff3ee00, 0x00000007c0000000)
     Metaspace       used 4627K, capacity 5066K, committed 5248K, reserved 1056768K
     class space    used 545K, capacity 562K, committed 640K, reserved 1048576K
     */
    @Test
    public void testParNew() {
        byte[] buffer = new byte[5 * 1024 * 1024];
    }

    /**
     * 老年代使用并行回收器, -XX:+UseParallelOldGC
     * 老年代都使用并行回收器，算法不变
     [GC (Allocation Failure) [PSYoungGen: 1022K->448K(1536K)] 1022K->448K(5632K), 0.0017143 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
     [GC (Allocation Failure) [PSYoungGen: 1468K->480K(1536K)] 1468K->536K(5632K), 0.0014896 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] [GC (Allocation Failure) [PSYoungGen: 1504K->496K(1536K)] 1560K->657K(5632K), 0.0013341 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
     [GC (Allocation Failure) [PSYoungGen: 1520K->512K(2560K)] 1681K->881K(6656K), 0.0012481 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] Heap
     PSYoungGen      total 2560K, used 2560K [0x00000007bfd00000, 0x00000007c0000000, 0x00000007c0000000)
     eden space 2048K, 100% used [0x00000007bfd00000,0x00000007bff00000,0x00000007bff00000)
     from space 512K, 100% used [0x00000007bff80000,0x00000007c0000000,0x00000007c0000000)
     to   space 512K, 0% used [0x00000007bff00000,0x00000007bff00000,0x00000007bff80000)
     ParOldGen       total 7168K, used 5489K [0x00000007bf600000, 0x00000007bfd00000, 0x00000007bfd00000)
     object space 7168K, 76% used [0x00000007bf600000,0x00000007bfb5c4a8,0x00000007bfd00000)
     Metaspace       used 4631K, capacity 5066K, committed 5248K, reserved 1056768K
     class space    used 545K, capacity 562K, committed 640K, reserved 1048576K
     */
    @Test
    public void testParOld() {
        byte[] buffer = new byte[5 * 1024 * 1024];
    }

    /**
     * 新生代和老年代都使用并行回收器 -XX:+UseParallelGC
     [GC (Allocation Failure) [PSYoungGen: 1022K->480K(1536K)] 1022K->480K(5632K), 0.0010097 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
     [GC (Allocation Failure) [PSYoungGen: 1500K->496K(1536K)] 1500K->544K(5632K), 0.0033315 secs] [Times: user=0.01 sys=0.00, real=0.01 secs]
     [GC (Allocation Failure) [PSYoungGen: 1520K->512K(1536K)] 1568K->633K(5632K), 0.0016483 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
     [GC (Allocation Failure) [PSYoungGen: 1536K->512K(2560K)] 1657K->868K(6656K), 0.0015243 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] Heap
     PSYoungGen      total 2560K, used 2560K [0x00000007bfd00000, 0x00000007c0000000, 0x00000007c0000000)
     eden space 2048K, 100% used [0x00000007bfd00000,0x00000007bff00000,0x00000007bff00000)
     from space 512K, 100% used [0x00000007bff80000,0x00000007c0000000,0x00000007c0000000)
     to   space 512K, 0% used [0x00000007bff00000,0x00000007bff00000,0x00000007bff80000)
     ParOldGen       total 7168K, used 5476K [0x00000007bf600000, 0x00000007bfd00000, 0x00000007bfd00000)
     object space 7168K, 76% used [0x00000007bf600000,0x00000007bfb59318,0x00000007bfd00000)
     Metaspace       used 4623K, capacity 5066K, committed 5248K, reserved 1056768K
     class space    used 545K, capacity 562K, committed 640K, reserved 1048576K
     */
    @Test
    public void testParallel() {
        byte[] buffer = new byte[5 * 1024 * 1024];
    }

}
