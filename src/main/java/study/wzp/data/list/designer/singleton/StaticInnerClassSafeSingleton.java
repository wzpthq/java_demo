package study.wzp.data.list.designer.singleton;

/**
 * 静态内部类实现
 */
public class StaticInnerClassSafeSingleton {

    private StaticInnerClassSafeSingleton() {

    }

    private static class SingletonHelper {

        private final static StaticInnerClassSafeSingleton instance = new StaticInnerClassSafeSingleton();

    }

    public static StaticInnerClassSafeSingleton getInstance() {
        return SingletonHelper.instance;
    }

}
