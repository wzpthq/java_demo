package study.wzp.data.list.part01.lession06.executor;

import java.util.concurrent.*;

/**
 * 目标：线程池执行任务时，如果异常了，打印堆栈信息
 */

public class Sample01 {


    public static void main(String[] args) throws InterruptedException {

        ExecutorService service = Executors.newFixedThreadPool(1);
        Future<Object> future = service.submit(new Callable<Object>() {

            @Override
            public Object call() throws Exception {
                return 1/0;
            }
        });

        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.getCause().printStackTrace();
        }

        service.shutdown();
        while(!service.isTerminated()) {
            service.awaitTermination(1, TimeUnit.SECONDS);
        }

    }

}
