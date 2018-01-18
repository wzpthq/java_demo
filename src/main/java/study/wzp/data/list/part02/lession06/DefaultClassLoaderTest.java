package study.wzp.data.list.part02.lession06;

/**
 * 默认的ClassLoader机制（双亲委派模式）
 *
 * 1. Bootstrap ClassLoader  加载lib/rt.jar, 可以通过XBootstrap设置你期望加载jar
 * 2. Extention ClassLoader  加载lib/ext/*.jar，
 * 3. Appliction ClassLoader 加载classpath类路径下的class；
 *
 */
public class DefaultClassLoaderTest {

    static class DefineClassLoaderTest {

        public static ClassLoader getClassLoader() {
            ClassLoader classLoader = null;

            classLoader = new ClassLoader() {
                @Override
                public Class<?> loadClass(String name) throws ClassNotFoundException {
                    return super.loadClass(name);
                }
            };

            return classLoader;
        }
    }

}
