package study.wzp.data.list.jvm;


import java.util.HashMap;
import java.util.Map;

public class HashMapTest {

    static class A {
        @Override
        public int hashCode() {
            return 1;
        }
    }

    public static void main(String[] args) {

        Map<Object, Object> map = new HashMap<>();
        A a1 = new A();
        A a2 = new A();
        map.put(a1, "A1");
        map.put(a2, "A2");

        System.out.println(map.get(a1) + " : " + map.get(a2));

    }

}
