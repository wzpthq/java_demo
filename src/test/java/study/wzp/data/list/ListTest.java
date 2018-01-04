package study.wzp.data.list;

import org.junit.Test;

public class ListTest {

    @Test
    public void testInsert() {

        List list = new List();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        // 第5个位置插入为7
        list.insert(5, 7);

        // 删除第7个元素
        list.remove(6);

        list.show();
    }

}
