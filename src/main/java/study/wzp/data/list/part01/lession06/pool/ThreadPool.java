package study.wzp.data.list.part01.lession06.pool;

/**
 * 定义线程池
 */

public interface ThreadPool {

    /** 提交任务 */
    void execute(Runnable task);

    /** 关闭线程池 */
    void shutdown();

}
