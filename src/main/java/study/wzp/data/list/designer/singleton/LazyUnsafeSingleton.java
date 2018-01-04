package study.wzp.data.list.designer.singleton;

/**
 * 懒汉
 */

public class LazyUnsafeSingleton {

    private static LazyUnsafeSingleton instance;

    private LazyUnsafeSingleton() {
        System.out.println("!==== Constructor ====!");
    }

    // unsafe
    public static LazyUnsafeSingleton getInstance() {
        if (instance == null) {
            instance = new LazyUnsafeSingleton();
        }
        return instance;
    }

    public static void show() {
        System.out.println("!==== show ====!");
    }

    public static void main(String[] args) {

        LazyUnsafeSingleton.show();

    }
}
