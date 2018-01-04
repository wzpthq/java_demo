package study.wzp.data.list.designer.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * CGlib实现动态代理
 *
 * 原理：通过继承代理目标类，子类方式覆盖父类的方法实现的
 */
public class CglibDynamicProxy {

    static class Man {

        public void sleep() {
            System.out.println(" Man Sleep!");
        }
    }

    /**
     * 实现MethodInterceptor
     */
    static class ManProxy implements MethodInterceptor  {

        Object target;

        public ManProxy(Object target) {
            this.target = target;
        }

        @Override
        public Object intercept(Object o, Method method, Object[] objects,
                                MethodProxy methodProxy) throws Throwable {

            Object res = null;

            System.out.println("====Before====");

            res = methodProxy.invoke(target, objects);

            System.out.println("====After====");

            return res;
        }
    }

    public static void main(String[] args) {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Man.class);
        enhancer.setCallback(new ManProxy(new Man()));
        Man man = (Man)enhancer.create();
        man.sleep();
    }
}

