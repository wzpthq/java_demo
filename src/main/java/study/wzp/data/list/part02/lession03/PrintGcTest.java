package study.wzp.data.list.part02.lession03;


import org.junit.Test;

import java.util.WeakHashMap;

public class PrintGcTest {

    /**
     * 使用-verbose:gc 和-XX:+PrintGC是一样的，打印GC信息
     */
    @Test
    public void testPrintGc() {

        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("name", "wangzhiping");
        map.put("age", "23");
        System.gc();
    }

    /**
     * 打印详细的GC信息
     * 使用-XX:+PrintGCDetails运行可以查看每个区的内存使用情况
     */
    @Test
    public void testPrintGcDetails() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("name", "wangzhiping");
        map.put("age", "23");
        System.gc();
    }


    @Test
    public void testPrintGcTimestamps() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        for (int i = 0; i < 1000; i ++) {
            map.put("" + i, "" + i);
        }
        System.gc();
    }

    /**
     * 当发生GC时，将GClog存放在某个问题
     * -Xloggc:path， 会配合使用-XX:+PrintGCDetails之类的打印GC信息指令使用，不然日志比较简单
     */
    @Test
    public void testGcLog() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        for (int i = 0; i < 1000; i ++) {
            map.put("" + i, "" + i);
        }
        System.gc();
    }

    /**
     * 在GC时，打印Gc前后的堆的使用情况
     {Heap before GC invocations=1 (full 0):
     PSYoungGen      total 38400K, used 8010K [0x0000000795580000, 0x0000000798000000, 0x00000007c0000000)
     eden space 33280K, 24% used [0x0000000795580000,0x0000000795d529c0,0x0000000797600000)
     from space 5120K, 0% used [0x0000000797b00000,0x0000000797b00000,0x0000000798000000)
     to   space 5120K, 0% used [0x0000000797600000,0x0000000797600000,0x0000000797b00000)
     ParOldGen       total 87552K, used 0K [0x0000000740000000, 0x0000000745580000, 0x0000000795580000)
     object space 87552K, 0% used [0x0000000740000000,0x0000000740000000,0x0000000745580000)
     Metaspace       used 4615K, capacity 5066K, committed 5248K, reserved 1056768K
     class space    used 542K, capacity 562K, committed 640K, reserved 1048576K
     Heap after GC invocations=1 (full 0):
     PSYoungGen      total 38400K, used 1216K [0x0000000795580000, 0x0000000798000000, 0x00000007c0000000)
     eden space 33280K, 0% used [0x0000000795580000,0x0000000795580000,0x0000000797600000)
     from space 5120K, 23% used [0x0000000797600000,0x0000000797730000,0x0000000797b00000)
     to   space 5120K, 0% used [0x0000000797b00000,0x0000000797b00000,0x0000000798000000)
     ParOldGen       total 87552K, used 8K [0x0000000740000000, 0x0000000745580000, 0x0000000795580000)
     object space 87552K, 0% used [0x0000000740000000,0x0000000740002000,0x0000000745580000)
     Metaspace       used 4615K, capacity 5066K, committed 5248K, reserved 1056768K
     class space    used 542K, capacity 562K, committed 640K, reserved 1048576K
     }
     {Heap before GC invocations=2 (full 1):
     PSYoungGen      total 38400K, used 1216K [0x0000000795580000, 0x0000000798000000, 0x00000007c0000000)
     eden space 33280K, 0% used [0x0000000795580000,0x0000000795580000,0x0000000797600000)
     from space 5120K, 23% used [0x0000000797600000,0x0000000797730000,0x0000000797b00000)
     to   space 5120K, 0% used [0x0000000797b00000,0x0000000797b00000,0x0000000798000000)
     ParOldGen       total 87552K, used 8K [0x0000000740000000, 0x0000000745580000, 0x0000000795580000)
     object space 87552K, 0% used [0x0000000740000000,0x0000000740002000,0x0000000745580000)
     Metaspace       used 4615K, capacity 5066K, committed 5248K, reserved 1056768K
     class space    used 542K, capacity 562K, committed 640K, reserved 1048576K
     Heap after GC invocations=2 (full 1):
     PSYoungGen      total 38400K, used 0K [0x0000000795580000, 0x0000000798000000, 0x00000007c0000000)
     eden space 33280K, 0% used [0x0000000795580000,0x0000000795580000,0x0000000797600000)
     from space 5120K, 0% used [0x0000000797600000,0x0000000797600000,0x0000000797b00000)
     to   space 5120K, 0% used [0x0000000797b00000,0x0000000797b00000,0x0000000798000000)
     ParOldGen       total 87552K, used 1110K [0x0000000740000000, 0x0000000745580000, 0x0000000795580000)
     object space 87552K, 1% used [0x0000000740000000,0x0000000740115830,0x0000000745580000)
     Metaspace       used 4615K, capacity 5066K, committed 5248K, reserved 1056768K
     class space    used 542K, capacity 562K, committed 640K, reserved 1048576K
     }

     */
    @Test
    public void testPrintHeapAtGc() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        for (int i = 0; i < 1000; i ++) {
            map.put("" + i, "" + i);
        }
        System.gc();
    }

}
