package study.wzp.data.list.designer.singleton;

/**
 * double-check实现的线程安全的单例
 */
public class DoubleCheckSafeSingleton {

    // 注意必须是volatile,保证线程的可见性
    private volatile static DoubleCheckSafeSingleton instance;

    private DoubleCheckSafeSingleton() {

    }


    /**
     * double-check
     *
     * 为了减少枷锁判断，提高效率；
     * @return
     */


    public static DoubleCheckSafeSingleton getInstance() {

        if (null == instance) { // 这里第一次判断，可以过滤掉!=null的情况，

            synchronized (instance) {
                // 第一次，如果判断是null，可能并发多个线程进入；
                // 第二次，使用了同步关键字，可以确保一个线程进入，这时候构造成功；第二个线程进入时
                // 就会是失败的。
                if (null == instance) {
                    // new操作不是原子性的
                    // 分配内存和在内存中创建对象
                    instance = new DoubleCheckSafeSingleton();
                }
            }

        }

        return instance;
    }


}
