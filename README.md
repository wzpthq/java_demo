# java_demo

## part02/lession03 ###
```
目的：练习使用JVM运行参数，集合GC情况；

```
### TraceTest
#### -XX:+PrintGC  -verbose:gc
```text
功能：打印简易的GC信息（感觉没啥用）
```
```
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

```

```text
[GC (System.gc())  7352K->1096K(125952K), 0.0026702 secs]
[Full GC (System.gc())  1096K->1012K(125952K), 0.0127374 secs]
```

#### -XX:+PrintGCDetails
```text
功能：打印GC详细信息；（可以帮助分析GC情况，老年代和新生代的情况）
```
```
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
```
```text
[GC (System.gc()) [PSYoungGen: 7352K->1104K(38400K)] 7352K->1112K(125952K), 0.0135003 secs] [Times: user=0.01 sys=0.00, real=0.02 secs] 
[Full GC (System.gc()) [PSYoungGen: 1104K->0K(38400K)] [ParOldGen: 8K->1013K(87552K)] 1112K->1013K(125952K), [Metaspace: 4612K->4612K(1056768K)], 0.0205659 secs] [Times: user=0.02 sys=0.00, real=0.02 secs] 
Heap
 PSYoungGen      total 38400K, used 665K [0x0000000795580000, 0x0000000798000000, 0x00000007c0000000)
  eden space 33280K, 2% used [0x0000000795580000,0x0000000795626688,0x0000000797600000)
  from space 5120K, 0% used [0x0000000797600000,0x0000000797600000,0x0000000797b00000)
  to   space 5120K, 0% used [0x0000000797b00000,0x0000000797b00000,0x0000000798000000)
 ParOldGen       total 87552K, used 1013K [0x0000000740000000, 0x0000000745580000, 0x0000000795580000)
  object space 87552K, 1% used [0x0000000740000000,0x00000007400fd540,0x0000000745580000)
 Metaspace       used 4635K, capacity 5066K, committed 5248K, reserved 1056768K
  class space    used 545K, capacity 562K, committed 640K, reserved 1048576K

```
```text
注意在jdk1.7之后，没有永久代了，换成了Metaspace（元空间，存放类信息）
```

#### -Xloggc:path

```text
功能：当GC时，会将GC信息重定向到path指定路径; 需要配合-XX:+PrintGCDetails之类的命令使用。
（感觉可以用来做GC监控，分析GC日志）
```

```text
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
```
```text
运行结果输出到了gc.log文件
Java HotSpot(TM) 64-Bit Server VM (25.144-b01) for bsd-amd64 JRE (1.8.0_144-b01), built on Jul 21 2017 22:07:42 by "java_re" with gcc 4.2.1 (Based on Apple Inc. build 5658) (LLVM build 2336.11.00)
Memory: 4k page, physical 8388608k(177644k free)

/proc/meminfo:

CommandLine flags: -XX:InitialHeapSize=134217728 -XX:MaxHeapSize=2147483648 -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseParallelGC 
0.767: [GC (System.gc()) [PSYoungGen: 7353K->1104K(38400K)] 7353K->1112K(125952K), 0.0135702 secs] [Times: user=0.01 sys=0.00, real=0.01 secs] 
0.781: [Full GC (System.gc()) [PSYoungGen: 1104K->0K(38400K)] [ParOldGen: 8K->1014K(87552K)] 1112K->1014K(125952K), [Metaspace: 4614K->4614K(1056768K)], 0.0132580 secs] [Times: user=0.01 sys=0.00, real=0.01 secs] 
Heap
 PSYoungGen      total 38400K, used 665K [0x0000000795580000, 0x0000000798000000, 0x00000007c0000000)
  eden space 33280K, 2% used [0x0000000795580000,0x0000000795626688,0x0000000797600000)
  from space 5120K, 0% used [0x0000000797600000,0x0000000797600000,0x0000000797b00000)
  to   space 5120K, 0% used [0x0000000797b00000,0x0000000797b00000,0x0000000798000000)
 ParOldGen       total 87552K, used 1014K [0x0000000740000000, 0x0000000745580000, 0x0000000795580000)
  object space 87552K, 1% used [0x0000000740000000,0x00000007400fd860,0x0000000745580000)
 Metaspace       used 4634K, capacity 5066K, committed 5248K, reserved 1056768K
  class space    used 545K, capacity 562K, committed 640K, reserved 1048576K
```


#### -XX:+PrintHeapAtGC
```text
功能：打印GC前后的堆情况；（可以用来做对比分析，GC后堆heap的详细变化）
```
```text
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
```
```text
{Heap before GC invocations=1 (full 0):
 PSYoungGen      total 38400K, used 7352K [0x0000000795580000, 0x0000000798000000, 0x00000007c0000000)
  eden space 33280K, 22% used [0x0000000795580000,0x0000000795cae3f8,0x0000000797600000)
  from space 5120K, 0% used [0x0000000797b00000,0x0000000797b00000,0x0000000798000000)
  to   space 5120K, 0% used [0x0000000797600000,0x0000000797600000,0x0000000797b00000)
 ParOldGen       total 87552K, used 0K [0x0000000740000000, 0x0000000745580000, 0x0000000795580000)
  object space 87552K, 0% used [0x0000000740000000,0x0000000740000000,0x0000000745580000)
 Metaspace       used 4611K, capacity 5066K, committed 5248K, reserved 1056768K
  class space    used 542K, capacity 562K, committed 640K, reserved 1048576K
Heap after GC invocations=1 (full 0):
 PSYoungGen      total 38400K, used 1104K [0x0000000795580000, 0x0000000798000000, 0x00000007c0000000)
  eden space 33280K, 0% used [0x0000000795580000,0x0000000795580000,0x0000000797600000)
  from space 5120K, 21% used [0x0000000797600000,0x0000000797714010,0x0000000797b00000)
  to   space 5120K, 0% used [0x0000000797b00000,0x0000000797b00000,0x0000000798000000)
 ParOldGen       total 87552K, used 8K [0x0000000740000000, 0x0000000745580000, 0x0000000795580000)
  object space 87552K, 0% used [0x0000000740000000,0x0000000740002000,0x0000000745580000)
 Metaspace       used 4611K, capacity 5066K, committed 5248K, reserved 1056768K
  class space    used 542K, capacity 562K, committed 640K, reserved 1048576K
}
{Heap before GC invocations=2 (full 1):
 PSYoungGen      total 38400K, used 1104K [0x0000000795580000, 0x0000000798000000, 0x00000007c0000000)
  eden space 33280K, 0% used [0x0000000795580000,0x0000000795580000,0x0000000797600000)
  from space 5120K, 21% used [0x0000000797600000,0x0000000797714010,0x0000000797b00000)
  to   space 5120K, 0% used [0x0000000797b00000,0x0000000797b00000,0x0000000798000000)
 ParOldGen       total 87552K, used 8K [0x0000000740000000, 0x0000000745580000, 0x0000000795580000)
  object space 87552K, 0% used [0x0000000740000000,0x0000000740002000,0x0000000745580000)
 Metaspace       used 4611K, capacity 5066K, committed 5248K, reserved 1056768K
  class space    used 542K, capacity 562K, committed 640K, reserved 1048576K
Heap after GC invocations=2 (full 1):
 PSYoungGen      total 38400K, used 0K [0x0000000795580000, 0x0000000798000000, 0x00000007c0000000)
  eden space 33280K, 0% used [0x0000000795580000,0x0000000795580000,0x0000000797600000)
  from space 5120K, 0% used [0x0000000797600000,0x0000000797600000,0x0000000797b00000)
  to   space 5120K, 0% used [0x0000000797b00000,0x0000000797b00000,0x0000000798000000)
 ParOldGen       total 87552K, used 1015K [0x0000000740000000, 0x0000000745580000, 0x0000000795580000)
  object space 87552K, 1% used [0x0000000740000000,0x00000007400fdc18,0x0000000745580000)
 Metaspace       used 4611K, capacity 5066K, committed 5248K, reserved 1056768K
  class space    used 542K, capacity 562K, committed 640K, reserved 1048576K
}
```

#### -XX:+TraceClassLoading

```text
功能：输出运行时加载了哪些类；
```
```text
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
```
```text
[Opened /Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home/jre/lib/rt.jar]
[Loaded java.lang.Object from /Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home/jre/lib/rt.jar]
[Loaded java.io.Serializable from /Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home/jre/lib/rt.jar]
[Loaded java.lang.Comparable from /Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home/jre/lib/rt.jar]
[Loaded java.lang.CharSequence from /Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home/jre/lib/rt.jar]
....

```
### HeapTest
#### -Xmx（最大堆空间）,-Xms（最小堆空间） 
```text
-Xmx: 最大堆空间；
-Xms: 最小堆空间；
@warning: 如果设置为奇数，那么实际上会+1，例如：-Xmx13m -Xms13m 最终实际上：-Xmx14m -Xms14m
```
```text
    /**
     * 运行参数:-Xmx10m -Xms10m -XX:+PrintGCDetails
     */
    @Test
    public void testXmxAndXms01() {
        // 分配1M的空间
        byte[] buffer = new byte[1 * 1024 * 1024];
    }

```

```text
[GC (Allocation Failure) [PSYoungGen: 2047K->496K(2560K)] 2047K->552K(9728K), 0.0011617 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] [GC (Allocation Failure) [PSYoungGen: 2544K->496K(2560K)] 2600K->855K(9728K), 0.0015136 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] Heap
 PSYoungGen      total 2560K, used 2541K [0x00000007bfd00000, 0x00000007c0000000, 0x00000007c0000000)
  eden space 2048K, 99% used [0x00000007bfd00000,0x00000007bfeff780,0x00000007bff00000)
  from space 512K, 96% used [0x00000007bff80000,0x00000007bfffc010,0x00000007c0000000)
  to   space 512K, 0% used [0x00000007bff00000,0x00000007bff00000,0x00000007bff80000)
 ParOldGen       total 7168K, used 1383K [0x00000007bf600000, 0x00000007bfd00000, 0x00000007bfd00000)
  object space 7168K, 19% used [0x00000007bf600000,0x00000007bf759f88,0x00000007bfd00000)
 Metaspace       used 4632K, capacity 5066K, committed 5248K, reserved 1056768K
  class space    used 545K, capacity 562K, committed 640K, reserved 1048576K

PSYoungGen: 一共2560k=eden(2048k) + from/to(512K) 
ParOldGen: 一共7168K
Metaspace: 是非堆空间，不做考虑；

```
```text
堆的分配问题，你会发现512K是浪费的，实际可用的空间没有10M，刚好是少了512K，那是因为在新生代我们采用了复制算法来做为垃圾回收，
复制算法会浪费空间；
```


### StackTest





