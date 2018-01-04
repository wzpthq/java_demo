package study.wzp.data.list.sample;

import study.wzp.data.list.LoopSinglyLinkedList;
import study.wzp.data.list.LoopSinglyLinkedList.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * 约瑟夫问题实践，通过单循环链表来实现构成一个环
 * 分析
 * 1、问题：给定人数，例如：N，给定计数器（M），从1计数，没到M就自杀。打印自杀顺序和最后不足M的人的位置
 */
public class JosephusProblem {

    // 循环链表，用于存放N
    private LoopSinglyLinkedList loopList = null;

    // 自杀阀值
    private int killCounter;

    // 存活人数
    private int alivePersonCount;

    public JosephusProblem(int killCounter) {
        this.loopList = new LoopSinglyLinkedList();
        this.killCounter = killCounter;
    }

    /**
     * 批量添加人到链表中
     * @param names
     */
    public void addPersons(String... names) {

        for (String name : names){
            loopList.addToTail(name);
        }

        this.alivePersonCount = loopList.getSize();
    }

    /**
     * 执行自杀过程我
     */
    public void kill() {

        if (killCounter <= 0) throw new IllegalArgumentException("Kill Count Illegal");



        Node p = loopList.getHead();

        int i = 0;

        while(true) {

            if (p.getNext() == loopList.getTail()) {
                p = p.getNext();
            }

            p = p.getNext();

            if (p.getData() == null) { // 已经自杀
                continue; // 不需要往下执行，执行下次循环
            }

            i++;

            if (i == killCounter) {  // 已经数完一轮
                i = 0;
                System.out.println(p.getData() + " is killed.");
                p.setData(null);     // 自杀
                alivePersonCount--; // 存活人数减1
            }

            if (alivePersonCount < killCounter) break;

        }


    }

    public static void main(String[] args) {
        JosephusProblem josephusProblem = new JosephusProblem(3);
        List<String> names = new ArrayList<String>();

        for (int i = 1; i <= 5; i ++) {
            names.add("wzp" + i);
        }

        String[] namess = new String[names.size()];
        names.toArray(namess);

        josephusProblem.addPersons(namess);

        josephusProblem.kill();


    }
}
