package study.wzp.data.list.sample;

import study.wzp.data.list.Stack;

/**
 * 进制转换
 */
public class BinaryExchangeSample {

    /**
     * 二进制转换成10进制
     * @param binaryStr
     * @return
     */
    public static double binary(String binaryStr, int mod) {
        Stack stack = new Stack(binaryStr.length());

        // 入栈
        for (int i = 0; i < binaryStr.length(); i ++){
            stack.push(binaryStr.substring(i, i + 1));
        }

        int n = 0;
        double v = 0;
        // 出栈
        while(stack.size() > 0) {
            v += Integer.valueOf(stack.pop().toString()) * Math.pow(mod, n);
            n ++;
        }

        return v;
    }

    public static void main(String[] args) {

        System.out.println(binary("10", 16));

    }

}
