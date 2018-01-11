package study.wzp.data.list.part02.lession05;

/**
 * 啥是静态绑定，说白了就是编译阶段就知道你属于那个类，那个方法，那个变量。
 * 其实包括：static final private 构造函数
 *
 * 然后我们解释为啥这些是静态绑定：
 *
 * 1、final: 如果一个变量被定义成了final，那么子类是不可以重写或者重载的，因此对于当前类是不变的，因此可以在编译阶段就可以确认；
 * 2、private: private修饰的不能内继承，属于类私有，因此可以提前确认关系；
 * 3、构造函数：如果是静态构造函数会确保clinit触发父类的clinit触发，然后实例构造函数的话，会显示的调用init；并且构造函数一定是1对1；
 * 4、static: 完全是属于类的，因此可以在编译时就确认其属于那个类，那个方法，那个变量；
 */
public class StaticBindTest {

    

}
