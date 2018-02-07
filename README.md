# what is max ?

帮助不善于使用lambda的开发者，进行高效的集合处理的工具类

# use case

1. 创建一个包含10个元素的自然数列 `Collection<Integer> list1 = CollectOperator.createIntsWithRange(10);` 输出结果 `[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]`
1. 同样可创建自己想要的数列，例如偶数 `Collection<Integer> list2 = CollectOperator.createIntsWithRange(0,2,10);` 输出结果 `[2, 4, 6, 8, 10, 12, 14, 16, 18, 20]`
1. 将上述两个数列进行求和计算 `CollectOperator.plus(list1,list2)` 输出结果 `[3.0, 6.0, 9.0, 12.0, 15.0, 18.0, 21.0, 24.0, 27.0, 30.0]` 这里默认会取数列最小的集合进行运算，结果为浮点型
1. 如果上述案例的数列不一致，改如何处理？ `CollectOperator.plus(CollectOperator.createIntsWithRange(10),CollectOperator.createIntsWithRange(0,2,15),true,1,1);` 将两个不同长度的序列进行加法，需要额外附加3个参数，ture/false 表示是否取最小集合，如果为false，后续2个参数无效，计算取最小集合，计算效果和上述案例相似，如果设置为true，则在计算的时候，不足的元素，按左右位置取默认值填充进行计算。
1. 将一个集合按索引生成键值对 
```
        //创建序列
        List<String> values = Arrays.asList("USA", "Japan", "France", "Germany", "Italy", "U.K.","Canada");
        
        //构建键值对
        //Map<Integer,String> map = CollectOperator.mergeToIndexMap(values);
``` 
输出结果 `{1=USA, 2=Japan, 3=France, 4=Germany, 5=Italy, 6=U.K., 7=Canada}` 
1. 将两个序列，对位匹配成键值对
```
        //创建键序列
        List<Integer> keys = Arrays.asList(10);
        
        //创建值序列
        List<String> values = Arrays.asList("USA", "Japan", "France", "Germany", "Italy", "U.K.","Canada");
        
        //构建键值对
        Map<Integer,String> map = CollectOperator.mergeToMap(keys,values,true);
```
输出结果`{10=USA}` 

# TODO
- [ ] 集合其他运算操作
- [ ] 创建矩阵
- [ ] 封装数据类型

# links

[码云](https://gitee.com/dafei1288/max)

[github](https://github.com/dafei1288/max)

(TBD...)