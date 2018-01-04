package study.wzp.data.list.designer.proxy;

/**
 * 静态代理，就是为每个要代理的对象，
 *
 * 缺点：就是会出现设计膨胀，如果有10000个类需要去代理，那么需要10000个代理类；
 *
 * ---->>>>>>> 动态代理：诞生 <<<<<<<< -----
 */

public class StaticProxy {


    static class Person {

        public void sleep() {
            System.out.println("I'm sleeping!");
        }
    }

    static class PersonProxy {

        // 代理的目标对象
        Person person;

        public PersonProxy(Person person) {
            this.person = person;
        }

        public void sleep() {
            System.out.println("======= Proxy Before =======");

            person.sleep();

            System.out.println("======= Proxy Before =======");
        }

    }


    public static void main(String[] args) {

        PersonProxy proxy = new PersonProxy(new Person());
        proxy.sleep();

    }

}
