package study.wzp.data.list.designer.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理：有很多的实现方
 *
 * JDK的实现
 * 要求：代理类和目标类，必须实现同一个接口
 *
 */
public class JdkDynamicProxy {

    static interface Person {
        public void sleep();
    }

    static class Man implements Person {
        @Override
        public void sleep() {
            System.out.println(" Man Sleep!");
        }
    }

    static class Woman implements Person {

        @Override
        public void sleep() {
            System.out.println(" Woman Sleep!");
        }
    }

    // 代理类，实现InvocationHandler
    static class PersonProxy implements InvocationHandler{

        Object target; // 目标类

        public PersonProxy(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            Object res = null;

            System.out.println("====== Invoke Before ======");

            res = method.invoke(target, args);

            System.out.println("====== Invoke After ======");

            return res;
        }
    }


    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        // 方式1：通过proxy获取使用
        Class clazz = Proxy.getProxyClass(
                Person.class.getClassLoader(), // 使用什么classloader来创建proxyclass
                Person.class // 代理类实现的接口列表，可以是多个
                );

        Person person = (Person)clazz.getConstructor(InvocationHandler.class).newInstance(new PersonProxy(new Man()));
        person.sleep();

        // 方式2：

        Person person2 = (Person)Proxy.newProxyInstance(Person.class.getClassLoader(), new Class[]{Person.class}, new PersonProxy(new Woman()));
        person2.sleep();
    }

}
