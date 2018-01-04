package study.wzp.data.list.sample;

import study.wzp.data.list.LinkedQueue;

/**
 * 汉诺塔问题
 */
public class HanoiSample {

    private LinkedQueue<Integer> a;

    private LinkedQueue<Integer> b;

    private LinkedQueue<Integer> c;

    private int number;

    public HanoiSample(int number) {
        this.number = number;
        this.a = new LinkedQueue<>();
        this.b = new LinkedQueue<>();
        this.c = new LinkedQueue<>();
        this.inA();
    }

    private void inA() {
        for (int i = number; i >= 1; i --) {
            a.enqueue(i);
        }
    }

    // 执行搬运过程
    public void exec() {

        if (a.isEmpty() &&  b.isEmpty() && c.size() == number) { // 搬运完了
            return;
        }

        // 将a队列全部一处
        int s = a.size();
        while(s > 1) {
            b.enqueue(a.dequeue());
            s --;
        }

        if (a.size() == 1){
            c.enqueue(a.dequeue()); //出队列入队列
            // 交换a，b

            LinkedQueue<Integer> t = new LinkedQueue<>();
            t = a;
            a = b;
            b = t;
        }

        exec();

    }

    public void show() {
        System.out.println("===========a========");
        a.show();
        System.out.println("===========b========");
        b.show();
        System.out.println("===========c========");
        c.show();
    }

    public static void main(String[] args) {
        HanoiSample sample = new HanoiSample(64);
        sample.exec();
        sample.show();
    }

}
