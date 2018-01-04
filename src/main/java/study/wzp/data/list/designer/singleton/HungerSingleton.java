package study.wzp.data.list.designer.singleton;

/**
 * 饿汉单例设计模式
 */

public class HungerSingleton {

    /** 申明定义时就构造，那么在类加载时，就会初始化该实例 */
    private static HungerSingleton instance = new HungerSingleton();

    private HungerSingleton(){
        System.out.println("====== Constructor ======");
    }

    public static HungerSingleton getInstance() {
        return instance;
    }

    public static void show() {
        System.out.println("====== show ======");
    }

    public static void main(String[] args) {

        // 缺点：未使用实例时，也会加载
        HungerSingleton.show();
    }

}
