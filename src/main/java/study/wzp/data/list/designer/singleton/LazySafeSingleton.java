package study.wzp.data.list.designer.singleton;

/**
 * 线程安全的单例
 */
public class LazySafeSingleton {

    private static LazySafeSingleton instance;

    private LazySafeSingleton() {
        System.out.println("!==== Constructor ====!");
    }

    // 同步关键字实现（方法级别）
    public synchronized static LazySafeSingleton getInstance() {
        if (instance == null) {
            instance = new LazySafeSingleton();
        }
        return instance;
    }

    // 同步关键字,代码块
    public static LazySafeSingleton getInstance1() {
        synchronized(instance) {

            if (instance == null) {
                instance = new LazySafeSingleton();
            }

        }
        return instance;
    }

    /**
     * getINstance 和 getInstance1实际上的效率是一样的；
     */

}
