package study.wzp.data.list.part01.lession06.executor;


import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.*;

/**
 * ForkJoinPool使用
 *
 * 0 - 200000 序列数相加，比较性能
 *
 */

public class Sample02 {

    public static ExecutorService service = Executors.newSingleThreadExecutor();

    public static ForkJoinPool forkJoinPool = new ForkJoinPool();

    @Timer
    public static void doWithThreadPoolExecutor() throws ExecutionException, InterruptedException {

        Future<Integer> future = service.submit(new Callable<Integer>() {

            @Override
            public Integer call() throws Exception {
                int sum = 0;
                for (int i = 0; i <= 1000000; i ++) {
                    sum += i;
                }
                return sum;
            }
        });

        System.out.println("1: "+ future.get());
    }

    static class SumTask extends RecursiveTask<Integer> {

        private int start;

        private int end;

        public SumTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            int sum = 0;
            if(end - start < 10) {
                for (int i = start; i <= end; i ++) {
                    sum += i;
                }
            }else {
                SumTask task1 = new SumTask(start, (end + start)/ 2);
                task1.fork();

                SumTask task2 = new SumTask((end + start) / 2 + 1,  end);
                task2.fork();

                int v1 = task1.join();
                int v2 = task2.join();
                sum = v1 + v2;
            }

            return sum;
        }
    }

    @Timer
    public static void doWithForkJoinExecutor() throws ExecutionException, InterruptedException {
        System.out.println("2: "+ forkJoinPool.submit(new SumTask(0, 1000000)).get());
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, ExecutionException, InterruptedException {

        Method[] methods = Sample02.class.getMethods();
        for(Method method : methods) {
            if(method.isAnnotationPresent(Timer.class)) {
                Annotation annotation = method.getAnnotation(Timer.class);
                long now = System.currentTimeMillis();
                method.invoke(Sample02.class, new Object[]{});
                System.out.println(method.getName() + ":" + (System.currentTimeMillis() - now));
            }

        }

        // doWithForkJoinExecutor();

    }

}
