## JVM内存模型

### 原子性
```$xslt

执行不会被其他线程干扰。

例如：i ++ ？并不是原子性的
```

### 有序性

```$xslt

计算机执行代码时，可能不会按照代码预期的顺序执行。

```

```java
class OrderSample {
    int a = 0;
    boolean flag = false;
    
    public void write(){
        a = 1; 
        flag = true;
    }
    
    public void read(){
        if (flag) {
            int i = a + 1;
            // TODO: ....
        }
    }
}
```

* 一条指令的执行（汇编指令）

```
- 取指： IF
- 取操作数：ID
- 执行有效地址计算：EX
- 存储访问：MEM
- 写回：WB

```

```
为啥不会完全按照我们写的顺序执行呢？因为执行效率问题，会进行
指令执行优化。

```