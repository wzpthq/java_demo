package study.wzp.data.list.sample;

import study.wzp.data.list.List;
import study.wzp.data.list.Stack;

/**
 * 逆波烂计算器（后缀表达式）
 * 1、输入一个中缀表达式（我们通常用的表达式），转换成后缀表达式；
 * 2、后缀表达式计算器计算；
 */
public class ReversePolishNotationSample {

    // 中缀表达式字符串
    private String[] middleExpression;

    // 后缀表达式
    private List suffixExpression;

    public ReversePolishNotationSample(String[] middleExpression) {
        this.middleExpression = middleExpression;
        this.suffixExpression = new List(middleExpression.length);
        middle2Suffix();
    }

    /**
     * 中缀表达式，转换成后缀表达式
     * #TODO 没有对操作符的优先级进行处理
     */
    private void middle2Suffix() {
        Stack stack = new Stack(100);

        // 遍历中缀表达式，进行转换
        for (String exp : middleExpression) {
            if (exp.equals("(") || isOperateChar(exp)) {
                stack.push(exp);
            }else if (exp.equals(")")) { // 如果是右括号，出栈
                while(!stack.peek().toString().equals("(")) {
                    suffixExpression.add(stack.pop());
                }
                stack.pop();
            }else {
                suffixExpression.add(exp);
            }
        }

        while(!stack.isEmpty()) {
            suffixExpression.add(stack.pop());
        }

    }

    /**
     * 计算后缀表达式结果
     * @return
     */
    public double cal() {
        Stack stack = new Stack(suffixExpression.size());

        String tmp = "";
        for (int i = 0; i < suffixExpression.size(); i ++) {
            tmp = suffixExpression.get(i).toString();
            if (!isOperateChar(tmp.toString())) { // 是操作数，入栈
                stack.push(tmp);
            } else { // 否则就是操作数
                int a2 = Integer.valueOf(stack.pop().toString());
                int a1 = Integer.valueOf(stack.pop().toString());
                int res = 0;
                if (tmp.equals("+")) {
                    res = Math.addExact(a1, a2);
                }else if (tmp.equals("-")) {
                    res = Math.subtractExact(a1,  a2);
                }else if (tmp.equals("*")){
                    res = Math.multiplyExact(a1, a2);
                }else if (tmp.equals("/")) {
                    res = Math.floorDiv(a1, a2);
                }
                stack.push(res);
            }
        }

        return Double.valueOf(stack.pop().toString());
    }

    /**
     * 判断字符是否只操作符
     * @param c
     * @return
     */
    private boolean isOperateChar(String c) {
        return c.equals("+") || c.equals("-") || c.equals("*") || c.equals("/");
    }


    public static void main(String[] args) {
        String[] p = new String[] {"(", "1", "+", "2", ")", "*", "3"};
        ReversePolishNotationSample r = new ReversePolishNotationSample(p);
        System.out.print(r.cal());
    }

}
