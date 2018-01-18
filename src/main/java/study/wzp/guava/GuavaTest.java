package study.wzp.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * google Guava unit test
 */

public class GuavaTest {

    private static Map<Integer, Student> localStudents = new HashMap<>();

    private static Map<Integer, Student> remoteStudents = new HashMap<>();

    static {
        for (int i = 0 ; i < 10; i ++) {
            localStudents.put(i, new Student(i, "wangzp" + i, 23 + i));
        }

        for (int i = 10 ; i < 10; i ++) {
            remoteStudents.put(i, new Student(i, "wangzp" + i, 23 + i));
        }
    }

    static class Student {

        private int id;

        private String name;

        private int age;

        public Student(int id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    public static Student getStudentById(int id) throws InterruptedException {
        Student student = localStudents.get(id);
        if (student != null) {
            System.out.println("local");
            return student;
        }else {
            System.out.println("remote");
            return remoteStudents.get(id);
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        LoadingCache<Integer, Student> studentCache = CacheBuilder
                .newBuilder()
                .maximumSize(6) // 最大被初始化的记录数据;
                .expireAfterAccess(5, TimeUnit.SECONDS) // 被访问后，多久释放
                .build(new CacheLoader<Integer, Student>() {
                    @Override
                    public Student load(Integer key) throws Exception {
                        return getStudentById(key);
                    }
                });

        // 从远程获取
        System.out.println(studentCache.get(1));
        System.out.println(studentCache.get(2));
        System.out.println(studentCache.get(3));

        System.out.println("================");

        // 这里会从cache获取
        System.out.println(studentCache.get(1));
        System.out.println(studentCache.get(2));
        System.out.println(studentCache.get(3));

        System.out.println("================");
        // 最大记录数测试
        System.out.println(studentCache.get(4));
        System.out.println(studentCache.get(1));
        System.out.println(studentCache.get(2));

        // 存活时间
        System.out.println("================");
        System.out.println(studentCache.get(4));
        Thread.sleep(6000);
        System.out.println(studentCache.get(4)); // 会过期


    }


}
