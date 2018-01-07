package study.wzp.data.list.part02.lession03;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 堆配置参数测试集合GC和分代分配情况
 */
public class HeapTest {

    /**
     * 目的：测试堆分配
     * 运行参数:-Xmx10m -Xms10m -XX:+PrintGCDetails
     */
    @Test
    public void testXmxAndXms01() {
        // 分配1M的空间
        byte[] buffer = new byte[1 * 1024 * 1024];
    }

    /**
     * 目的：测试GC情况
     * 运行参数：
     * 第一组：-Xmx10m -Xms5m -XX:+PrintGCDetails
     * 第二组：-Xmx10m -Xms7m -XX:+PrintGCDetails
     * 第三组：-Xmx10m -Xms10m -XX:+PrintGCDetails
     *
     * 一般都会设置-Xmx==-Xms目的是减少GC次数
     */
    @Test
    public void testXmxAndXms02() {
        byte[] buffer = new byte[5 * 1024 * 1024];
    }

    /**
     * 目的：测试分配新生代空间
     * 运行参数：-Xmx10m -Xms10m -Xmn2m -XX:+PrintGCDetails
     * 结果：就会发现新生代2M，老年代8M
     */
    @Test
    public void testXmn() {
        byte[] buffer = new byte[1 * 1024 * 1024];
    }

    /**
     * 目的：测试新生代与老年代的比例
     * 运行参数：-Xmx10m -Xms10m -XX:NewRatio=4 -XX:+PrintGCDetails
     * 结果：堆大小10M，NewRatio=4表示新生代:老年代=1:4，那么就是新生代10*0.25 老年代10*0.75
     */
    @Test
    public void testNewRatio() {
        byte[] buffer = new byte[1 * 1024 * 1024];
    }

    /**
     * 目的：测试新生代from/to与eden的比例
     * 运行参数：-Xmx10m -Xms10m -Xmn4m -XX:SurvivorRatio=2 -XX:+PrintGCDetails
     * 结果：eden 2048K, from/to 1024k ，并且有限制，最小是512K
     */
    @Test
    public void testSurvivorRatio() {
        byte[] buffer = new byte[1 * 1024 * 1024];
    }


    /**
     * 目的：当OOM时，将OOM日志输出到指定文件
     * 运行参数：-Xmx10m -Xms10m -XX:+HeapDumpOnOutOfMemoryError
     * 结果：OOM发生时，会输出OOM文件,java_pid[pid号].hprof，也可以通过-XX:+HeapDumpPath
     */
    @Test
    public void testOOMParameter() {
        byte[] buffer = new byte[10 * 1024 * 1024];
    }


}
