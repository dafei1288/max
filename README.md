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

在java8中的java.util.function包下预定义了大量函数式接口，典型的包含如下4类接口：
1. XxxFuncion：这类接口通常包含一个apply()抽象方法，该方法对参数进行处理，转换，然后返回一个新的值。具体的apply()方法的处理逻辑由lambda表达式来实现。该函数式接口通常用于对指定数据进行转换处理
1. XxxConsumer：这类接口通常包含一个accept()抽象方法，这个方法与上面的XxxFunction接口中的apply()方法基本相似，也是负责对参数进行处理，只是该方法不会返回处理结果。
1. XxxPredicate：这类接口通常包含一个test()抽象方法，该方法通常用来对参数进行某种判断，然后返回一个boolean值。具体的test()方法的判断逻辑由lambda表达式来实现，这个接口用于判断参数是否满足特定条件，经常用于过滤数据。
1. XxxSupplier：这类接口通常包含一个getAsXxx()抽象方法，该方法不需要输入参数，该方法会按某种逻辑算法来返回一个数据。

选择一个函数式接口
在大多数函数式编程语言中，函数类型都是结构化的。为了指定将2个字符串映射到一个整数的函数，我们需要使用一个类似于Function2<String,String,Integer>或者(String,String)->int的类型。不过在java中，我们直接使用Comparator<String>这样的函数式接口来声明函数就OK。在许多情况下，我们希望接受任意的函数，而不是某种特定语义的函数，java8已经给我们提供了许多的函数类型，我们要尽可能的使用它们。现在这里做一个整理：
1. Runnable：public abstract void run()：执行一个没有参数和返回值的操作
1. Supplier<T>：T get()：提供一个T类型的值
1. Comsumer<T>：void accept(T t)：处理一个T类型的值
1. BiComsumer<T,U>：void accept(T t, U u)：处理T类型和U类型的值
1. Function<T,R>：R apply(T t)：处理一个参数类型是T的函数，返回R
1. BiFunction<T,U,R>：R apply(T t, U u)：处理参数类型是T，U的函数，返回R
1. UnaryOperator<T>：T apply(T t)：对类型T进行一元操作，这个接口extends Function<T, T>
1. BinaryOperator<T>：T apply(T t,T t)：对类型T进行二元操作，这个接口extends Function<T, T, T>
1. Predicate<T>：boolean test(T t)：对类型T进行计算，返回boolean值
1. BiPredicate<T, U>：boolean test(T t, U u)：对类型T和U进行计算boolean值

# TODO
- [x] 集合四则运算运算操作
- [x] 关系代数运算
- [ ] ~~封装数据类型~~
- [ ] ~~表达式引擎~~
- [x] TupleTable 及 TuplePivotTable 原型实现
- [x] TupleTable 及 TuplePivotTable 特性演化（TupleTable 添加 内连接、全外连接、左外连接、右外连接）
- [ ] 文档补全
- [x] 添加doc
- [x] 测试用例

# refs
1. [java8-functional-interfaces[1]](http://www.runoob.com/java/java8-functional-interfaces.html)
1. [java8新特性 lambda Stream map(函数式编程)[2]](http://blog.csdn.net/u014646662/article/details/52261511)
1. [common-sql-clauses-and-their-equivalents-in-java-8-streams[3]](https://blog.jooq.org/2015/08/13/common-sql-clauses-and-their-equivalents-in-java-8-streams/)
1. [how-translate-sql-group-and[4]](https://dzone.com/articles/how-translate-sql-group-and)
# links

1. [码云](https://gitee.com/dafei1288/max)

1. [github](https://github.com/dafei1288/max)

(update@2018/3/10)