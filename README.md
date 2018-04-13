# what is max ?

帮助不善于使用lambda的开发者，进行高效的集合处理的工具类。

> 那么为什么叫max？ 因为最近越发的喜欢《破产姐妹》里面的max了。

# coordinate
`SNAPSHOT`:
> https://oss.sonatype.org/content/groups/public

> compile 'com.dafei1288:max:1.0-SNAPSHOT'

`RELEASE`:

> compile 'com.dafei1288:max:0.0.1-alpha'

# something new

`TableStream` 用于创建类`表`结构，以`Tuple`为行信息记录数据，从而形成类似二维表结构， `TupleList`为扩展集合容器，推荐通过`tableStream()`获得流，以免出现流占用异常。
data包主要功能提供加载数据的便捷工具类。

举个栗子：

```
//加载CSV文件，
//参数分别代表：CSV路径，分隔符，是否包含行标头
TupleList<Tuple> tl = CsvLoader.loadDataToTupleList("C:\\Users\\dafei\\Desktop\\rb.csv",",",false);

//显示前10条记录
tl.tableStream().head(10).forEach(System.out::println);

```

再举个栗子：
```
        //创建列变换函数
        Function<String,Integer> a0 = (x)->(Integer.parseInt(x)+1);
        Function<String,String> a3 = x->"aaaa";
        
        //构建变换函数对应的索引列图
        Hashtable<Integer,Function> tansTable = new Hashtable<>();
        tansTable.put(0,a0);
        tansTable.put(3,a3);
        
        CsvLoader.loadDataToTableStream("src/test/resources/train.csv",",",true).trans(tansTable).forEach(System.out::println);
        //(2, 0, 3, aaaa,  Mr. Owen Harris", male, 22, 1, 0, A/5 21171, 7.25, , S)
        //(3, 1, 1, aaaa,  Mrs. John Bradley (Florence Briggs Thayer)", female, 38, 1, 0, PC 17599, 71.2833, C85, C)
        //(4, 1, 3, aaaa,  Miss. Laina", female, 26, 0, 0, STON/O2. 3101282, 7.925, , S)
        
        CsvLoader.loadDataToTableStream("src/test/resources/train.csv",",",true).transFilter(tansTable).forEach(System.out::println);
        //(aaaa, 2)
        //(aaaa, 3)
        //(aaaa, 4)
        
```

经典wordcount：

```
    TableStream.load(Arrays.asList("jacky","tom","jacky","naccy","black","tom","tom")).mappedToKeyAndReduceCountBy(it->it).entrySet().forEach(System.out::println);
    //tom=3
    //black=1
    //jacky=2
    //naccy=1
```

多列聚合：

```
    //数据
    0,1,2 //列索引
    1,"张三",3000.00
    2,"李四",2400.00
    3,"王五",12200.00
    4,"赵六",9000.00
    5,"马七",21000.00
    5,"马七",21000.00
    
    //tt-> TupleList数据集合
    //Arrays.asList(0,2,0,2,0,2,0,2) 测试重复聚合字段
    //Arrays.asList(0,1) ID
    Map<Tuple,Tuple> mtt = tt.aggregateMultiColumsBy(Arrays.asList(0,2,0,2,0,2,0,2),Arrays.asList(0,1));
    mtt.forEach((k,v)->{System.out.println("k = "+k+" , v = "+v);});
    
    //k = (5, 马七) , v = (10.0, 42000.0, 10.0, 42000.0, 10.0, 42000.0, 10.0, 42000.0)
    //k = (2, 李四) , v = (2.0, 2400.0, 2.0, 2400.0, 2.0, 2400.0, 2.0, 2400.0)
    //k = (1, 张三) , v = (1.0, 3000.0, 1.0, 3000.0, 1.0, 3000.0, 1.0, 3000.0)
    //k = (4, 赵六) , v = (4.0, 9000.0, 4.0, 9000.0, 4.0, 9000.0, 4.0, 9000.0)
    //k = (3, 王五) , v = (3.0, 12200.0, 3.0, 12200.0, 3.0, 12200.0, 3.0, 12200.0)
```

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

9. [占比计算](https://github.com/dafei1288/max/wiki/%E5%8D%A0%E6%AF%94%E8%AE%A1%E7%AE%97)

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
- [x] 添加 TableStream
- [ ] 文档补全
- [x] 添加doc
- [x] 测试用例

# refs
1. [java8-functional-interfaces[1]](http://www.runoob.com/java/java8-functional-interfaces.html)
1. [java8新特性 lambda Stream map(函数式编程)[2]](http://blog.csdn.net/u014646662/article/details/52261511)
1. [common-sql-clauses-and-their-equivalents-in-java-8-streams[3]](https://blog.jooq.org/2015/08/13/common-sql-clauses-and-their-equivalents-in-java-8-streams/)
1. [how-translate-sql-group-and[4]](https://dzone.com/articles/how-translate-sql-group-and)
1. [micro-benchmarking-with-jmh-measure-dont-guess[5]](https://antoniogoncalves.org/2015/01/15/micro-benchmarking-with-jmh-measure-dont-guess/)
1. [introduction-jmh-profilers[6]](http://java-performance.info/introduction-jmh-profilers/)

# links

1. [码云](https://gitee.com/dafei1288/max)

1. [github](https://github.com/dafei1288/max)

(update@2018/4/13)