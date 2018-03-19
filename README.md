# what is max ?

帮助不善于使用lambda的开发者，进行高效的集合处理的工具类。

> 那么为什么叫max？ 因为最近越发的喜欢《破产姐妹》里面的max了。

# coordinate
`SNAPSHOT`:
> https://oss.sonatype.org/content/groups/public

> compile 'com.dafei1288:max:1.0-SNAPSHOT'

`RELEASE`:

> doing 


# use case

1. 创建一个包含10个元素的自然数列 `Collection<Integer> list1 = CollectOperator.createIntsWithRange(10);` 输出结果 `[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]`
2. 同样可创建自己想要的数列，例如偶数 `Collection<Integer> list2 = CollectOperator.createIntsWithRange(0,2,10);` 输出结果 `[2, 4, 6, 8, 10, 12, 14, 16, 18, 20]`
3. 将上述两个数列进行求和计算 `CollectOperator.plus(list1,list2)` 输出结果 `[3.0, 6.0, 9.0, 12.0, 15.0, 18.0, 21.0, 24.0, 27.0, 30.0]` 这里默认会取数列最小的集合进行运算，结果为浮点型
4. 如果上述案例的数列不一致，改如何处理？ `CollectOperator.plus(CollectOperator.createIntsWithRange(10),CollectOperator.createIntsWithRange(0,2,15),true,1,1);` 将两个不同长度的序列进行加法，需要额外附加3个参数，ture/false 表示是否取最小集合，如果为false，后续2个参数无效，计算取最小集合，计算效果和上述案例相似，如果设置为true，则在计算的时候，不足的元素，按左右位置取默认值填充进行计算。
5. 将一个集合按索引生成键值对 
```
        //创建序列
        List<String> values = Arrays.asList("USA", "Japan", "France", "Germany", "Italy", "U.K.","Canada");
        
        //构建键值对
        //Map<Integer,String> map = CollectOperator.mergeToIndexMap(values);
``` 
输出结果 `{1=USA, 2=Japan, 3=France, 4=Germany, 5=Italy, 6=U.K., 7=Canada}` 

6. 将两个序列，对位匹配成键值对
```
        //创建键序列
        List<Integer> keys = Arrays.asList(10);
        
        //创建值序列
        List<String> values = Arrays.asList("USA", "Japan", "France", "Germany", "Italy", "U.K.","Canada");
        
        //构建键值对
        Map<Integer,String> map = CollectOperator.mergeToMap(keys,values,true);
```
输出结果`{10=USA}` 

7. 探测序列内，是否每一个元素都符合表达式
```
        Collection<Integer> evet = CollectOperator.createRandomInts(10);
        Boolean tag = CollectOperator.everyOne(evet,x -> x >= 10);
```
同样还有，是否有元素符合要求
```
        Collection<Integer> evet = CollectOperator.createRandomInts(10);
        Boolean tag = CollectOperator.hasOne(evet,x -> x >= 10);
```
是否全部不符合要求 
```
        Collection<Integer> evet = CollectOperator.createRandomInts(10);
        Boolean tag = CollectOperator.noOne(evet,x -> x >= 10);
``` 

8. 过滤出符合条件的元素
```
        Collection<Integer> evet = CollectOperator.createIntsWithRange(10);
        Collection<Integer> fixs = CollectOperator.everyFixTo(evet,x -> x >= 5);
        //结果：[5,6,7,8,9,10]
```

# lambda know how

Java SE 7中已经存在的函数式接口：
```
java.lang.Runnable
java.util.concurrent.Callable
java.security.PrivilegedAction
java.util.Comparator
java.io.FileFilter
java.beans.PropertyChangeListener
```

除此之外，Java SE 8中增加了一个新的包：java.util.function，它里面包含了常用的函数式接口，例如：

`redicate<T>` ——接收`T`对象并返回`boolean` 

`Consumer<T>`——接收`T`对象，不返回值 

`Function<T, R>`——接收`T`对象，返回`R`对象

`Supplier<T>`——提供`T`对象（例如工厂），不接收值

`UnaryOperator<T>`——接收`T`对象，返回`T`对象

`BinaryOperator<T>`——接收两个`T`对象，返回`T`对象

除了上面的这些基本的函数式接口，我们还提供了一些针对原始类型（Primitive type）的特化（Specialization）函数式接口，例如`IntSupplier`和`LongBinaryOperator`。（我们只为`int`、`long`和`double`提供了特化函数式接口，如果需要使用其它原始类型则需要进行类型转换）同样的我们也提供了一些针对多个参数的函数式接口，例如`BiFunction<T, U, R>`，它接收`T`对象和`U`对象，返回`R`对象。

# TODO
- [x] 集合四则运算运算操作
- [x] 关系代数运算
- [ ] ~~封装数据类型~~
- [ ] ~~表达式引擎~~
- [x] TupleTable 及 TuplePivotTable 原型实现
- [x] TupleTable 及 TuplePivotTable 特性演化（TupleTable 添加 内连接、全外连接、左外连接、右外连接）
- [ ] 文档补全
- [x] 测试用例

# refs
1. [java8-functional-interfaces[1]](http://www.runoob.com/java/java8-functional-interfaces.html)
1. [java8新特性 lambda Stream map(函数式编程)[2]](http://blog.csdn.net/u014646662/article/details/52261511)
1. [common-sql-clauses-and-their-equivalents-in-java-8-streams[3]](https://blog.jooq.org/2015/08/13/common-sql-clauses-and-their-equivalents-in-java-8-streams/)

# links

1. [码云](https://gitee.com/dafei1288/max)

1. [github](https://github.com/dafei1288/max)

(update@2018/3/10)