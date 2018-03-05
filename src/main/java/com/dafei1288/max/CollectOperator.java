package com.dafei1288.max;

import com.dafei1288.max.functor.BaseOperator;
import com.dafei1288.max.functor.NaturalSupplier;
import com.dafei1288.max.functor.RandomSupplier;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 * 集合操作工具类
 * @author dafei1288
 * @version 0.1
 *
 * */
public class CollectOperator {
    /**
     *  去掉空值，并全部转换成大写字母
     *  @param list
     *     操作集合
     *
     *  @return
     * */
    public static Collection<?> toUpperCaseExcludeNull(Collection<?> list){
        return toUpperCase(removeNullElement(list),"");
    }


    /**
     *  并全部转换成大写字母，空值以默认值代替
     *  @param list
     *     操作集合
     *  @param defaultValue
     *      默认值
     *  @return
     * */
    public static Collection<?> toUpperCase(Collection<?> list,Object defaultValue){
        return list
                .stream()
                .map(it->
                        {
                            Optional temp  = Optional.ofNullable(it);
                            return temp.orElseGet(()->defaultValue).toString().toUpperCase();
                        }
                )
                .collect(Collectors.toList());
    }

    /**
     *  并全部转换成大写字母，空值以空字符串代替
     *  @param list
     *     操作集合
     *
     *  @return
     * */
    public static Collection<?> toUpperCase(Collection<?> list){
        return toUpperCase(list,"");
    }

    /**
     * 将集合中的空值去掉
     * @param list
     *     操作集合
     * @return
     * */
    public static Collection<?> removeNullElement(Collection<?> list){
        return list.stream().filter(it->it!=null).collect(Collectors.toList());
    }

    /**
     *  去掉空值，并全部转换成小写字母
     *  @param list
     *     操作集合
     *  @return
     * */
    public static Collection<?> toLowerCaseExcludeNull(Collection<?> list){
        return toUpperCase(removeNullElement(list),"");
    }


    /**
     *  并全部转换成小写字母，空值以默认值代替
     *  @param list
     *     操作集合
     *  @param defaultValue
     *     默认值
     *
     *  @return
     * */
    public static Collection<?> toLowerCase(Collection<?> list,Object defaultValue){
        return list
                .stream()
                .map(it->
                        {
                            Optional temp  = Optional.ofNullable(it);
                            return temp.orElseGet(()->defaultValue).toString().toLowerCase();
                        }
                )
                .collect(Collectors.toList());
    }

    /**
     *  并全部转换成小写字母，空值以空字符串代替
     *  @param list
     *     操作集合
     *  @return
     * */
    public static Collection<?> toLowerCase(Collection<?> list){
        return toLowerCase(list,"");
    }

    public static <T extends Number> Collection<Double> plus(Collection<? extends Number> list,T value){
        return twoElementsOperation(list,value,BaseOperator.PLUS);
    }


    public static <T extends Number> Collection<Double> minus(Collection<? extends Number> list,T value){
        return twoElementsOperation(list,value,BaseOperator.MINUS);
    }

    public static <T extends Number> Collection<Double> mulit(Collection<? extends Number> list,T value){
        return twoElementsOperation(list,value,BaseOperator.MULTI);
    }

    public static <T extends Number> Collection<Double> divide(Collection<? extends Number> list,T value){
        return twoElementsOperation(list,value,BaseOperator.DIVIDE);
    }

    public static <T extends Number> Collection<Double> twoElementsOperation(Collection<? extends Number> list,T value,BaseOperator operator){
        return list.stream().map(it-> {
            Optional t1  = Optional.ofNullable(it);
            Optional t2  = Optional.ofNullable(value);
            Double td1 = Double.valueOf(t1.orElse(0).toString());
            Double td2 = Double.valueOf(t2.orElse(0).toString());
            switch (operator){
                case PLUS: return td1+td2;
                case MINUS: return td1-td2;
                case MULTI: return td1*td2;
                case DIVIDE: return td1/td2;
                default: return td1+td2;
            }

        }).collect(Collectors.toList());
    }



    public static Collection<Double> plus(Collection<? extends Number> lefts,Collection<? extends Number> rights){
        return plus(lefts,rights,false,0,0);
    }

    public static <T extends Number> Collection<Double> plus(Collection<? extends Number> lefts,Collection<? extends Number> rights,Boolean overflow,T leftDefaultValue , T rightDefaultValue){
        return twoSetsOperation(lefts,rights,false,0,0,BaseOperator.PLUS);
    }


    public static Collection<Double> minus(Collection<? extends Number> lefts,Collection<? extends Number> rights){
        return minus(lefts,rights,false,0,0);
    }

    public static <T extends Number> Collection<Double> minus(Collection<? extends Number> lefts,Collection<? extends Number> rights,Boolean overflow,T leftDefaultValue , T rightDefaultValue){
        return twoSetsOperation(lefts,rights,false,0,0,BaseOperator.MINUS);
    }


    public static Collection<Double> mulit(Collection<? extends Number> lefts,Collection<? extends Number> rights){
        return mulit(lefts,rights,false,0,0);
    }

    public static <T extends Number> Collection<Double> mulit(Collection<? extends Number> lefts,Collection<? extends Number> rights,Boolean overflow,T leftDefaultValue , T rightDefaultValue){
        return twoSetsOperation(lefts,rights,false,0,0,BaseOperator.MULTI);
    }


    public static Collection<Double> divide(Collection<? extends Number> lefts,Collection<? extends Number> rights){
        return divide(lefts,rights,false,0,0);
    }

    public static <T extends Number> Collection<Double> divide(Collection<? extends Number> lefts,Collection<? extends Number> rights,Boolean overflow,T leftDefaultValue , T rightDefaultValue){
        return twoSetsOperation(lefts,rights,false,0,0,BaseOperator.DIVIDE);
    }

    public static <T extends Number> Collection<Double> twoSetsOperation(Collection<? extends Number> lefts,Collection<? extends Number> rights,Boolean overflow,T leftDefaultValue , T rightDefaultValue,BaseOperator operator){
        List<Double> temp = new ArrayList<>();
        int leftSize = lefts.size();
        int rightSize = rights.size();
        int min = NumberUtils.min(leftSize,rightSize);
        int max = NumberUtils.max(leftSize,rightSize);
        int setps = overflow ? max : min;
        for(int i = 0; i < setps ; i++){
            Optional left  = Optional.ofNullable(i<lefts.size()?lefts.toArray()[i]:null);
            Optional right  = Optional.ofNullable(i<rights.size()?rights.toArray()[i]:null);
            Double leftd = Double.valueOf(left.orElse(leftDefaultValue).toString());
            Double rightd = Double.valueOf(right.orElse(rightDefaultValue).toString());
            switch (operator){
                case PLUS:  temp.add(leftd+rightd);break;
                case MINUS: temp.add(leftd-rightd);break;
                case MULTI: temp.add(leftd*rightd);break;
                case DIVIDE: temp.add(leftd/rightd);break;
                default: break ;
            }

        }

        return temp;
    }
    /**
     * 将两个集合，合并成字典，字典数量为键集合的数量
     * @param key
     *  作为键
     * @param value
     *  作为值
     * @param fillEmpty
     *  是否以空值填充键队列
     *
     *  @return
     * */
    public static <K,V> Map<K,V> mergeToMap(Collection<K> key,Collection<V> value,Boolean fillEmpty){
        HashMap<K,V> mapper = new HashMap<>();
        Object[] ka = key.toArray();
        Object[] va = value.toArray();
        int setps = ka.length > va.length ? va.length : ka.length;
        for(int i=0; i < setps ; i++){
            mapper.put((K)ka[i],(V)va[i]);
        }
        if(ka.length>va.length && fillEmpty){
            for(int i=va.length; i < ka.length ; i++){
                mapper.put((K)ka[i],null);
            }
        }

        return mapper;
    }

    /**
     * 将两个集合，合并成字典，字典数量为键，值两个队列的最小值
     * @param key
     *  作为键
     * @param value
     *  作为值
     *
     *  @return
     * */
    public static <K,V> Map<K,V> mergeToMap(Collection<K> key,Collection<V> value){
        return mergeToMap(key,value,false);
    }

    /**
     * 将两个集合，合并成字典，字典数量为键集合的数量，没有值以空值代替
     * @param key
     *  作为键
     * @param value
     *  作为值
     *
     *  @return
     * */
    public static <K,V> Map<K,V> mergeToMapWithAllKeys(Collection<K> key,Collection<V> value){
        return mergeToMap(key,value,true);
    }

    /**
     * 为集合创建自然数索引
     *
     * @param values
     *  要创建索引的集合
     *
     * @return
     * */
    public static <V> Map<Integer,V> mergeToIndexMap(Collection<V> values){
        Collection<Integer> keys = createIntsWithRange(values.size());
        return mergeToMapWithAllKeys(keys,values);
    }

    /**
     * 创建自然数集合，从1开始，count个
     * @param count
     *  计数器
     *
     *  @return
     * */
    public static Collection<Integer> createIntsWithRange(Integer count){
        return createIntsWithRange(0,1,count);
    }
    /**
     * 创建整数列表
     *
     * 以from值开始，按照步长累加，创建count个
     * @param from
     *  起始数据
     * @param setp
     *  步长
     * @param count
     *  计数器
     *
     * @return
     * */
    public static Collection<Integer> createIntsWithRange(Integer from,Integer setp,Integer count){
        Stream<Integer> natural = Stream.generate(new NaturalSupplier(from,setp));
        return natural.limit(count).collect(Collectors.toList());
    }

    /**
     * 创建以0填充的集合，参数为个数
     * @param count
     *  计数器
     *
     *  @return
     * */
    public static Collection<Integer> createZeros(Integer count){
        return createIntsWithRange(0,0,count);
    }

    /**
     * 创建以1填充的集合，参数为个数
     * @param count
     *  计数器
     *
     *  @return
     * */
    public static Collection<Integer> createOnes(Integer count){
        return createIntsWithRange(1,0,count);
    }


    /**
     * 创建随机数集合，参数为个数
     * @param count
     *  计数器
     *
     *  @return
     * */
    public static Collection<Double> createRandoms(Integer count){
        Stream<Double> natural = Stream.generate(RandomSupplier.RANDOM_DOUBLE_SEED);
        return natural.limit(count).collect(Collectors.toList());
    }

    /**
     * 创建随机数集合，参数为个数
     * @param count
     *  计数器
     *
     *  @return
     * */
    public static Collection<Integer> createRandomInts(Integer count){
        Stream<Integer> natural = Stream.generate(RandomSupplier.RANDOM_INT_SEED);
        return natural.limit(count).collect(Collectors.toList());
    }


    public static Collection<Integer> createRangeInts(int startInclusive, int endExclusive){
        return IntStream.range(startInclusive,endExclusive).boxed().collect(Collectors.toList());
    }

    public static Collection<Integer> createRangeInts(int startInclusive, int endExclusive,int setp){
        final int s = startInclusive;
        final int p = setp;
        return IntStream.range(startInclusive,endExclusive).filter(
                item->{
                    return ((item-s)%p)==0;
                }
        ).boxed().collect(Collectors.toList());
    }
    /**
     * 创建布尔值集合，参数为个数
     * @param count
     *  计数器
     *
     *  @return
     * */
    public static Collection<Boolean> createBooleans(Integer count){
        Stream<Boolean> natural = Stream.generate(RandomSupplier.RANDOM_BOOLEAN_SEED);
        return natural.limit(count).collect(Collectors.toList());
    }

    /**
     * 创建以默认值填充的集合，参数为个数
     * @param count
     *  计数器
     * @param value
     *  默认值
     *
     *  @return
     * */
    public static Collection<Integer> createIntsWithDefaultValue(Integer count,Integer value){
        return createIntsWithRange(value,0,count);
    }



    /**
     * 测试每个元素是否符合表达式
     * @param list
     *  操作集合
     * @param predicate
     *  表达式 类似 x-&gt; x &gt; 10
     *
     *  @return
     * */
    public static <T> Boolean everyOne(Collection<T> list, Predicate<T> predicate){
////        Boolean temp = Boolean.TRUE;
//        for(T num:list){
//            if(predicate.test(num) != tag){
//                return Boolean.FALSE;
//            }
//        }
        return list.stream().allMatch(predicate);
    }

    /**
     * 测试集合内是否包含某个元素
     * @param list
     *  操作集合
     * @param predicate
     *  表达式 类似 x-&gt; x &gt; 10
     *
     *  @return
     * */
    public static <T> Boolean hasOne(Collection<T> list, Predicate<T> predicate){
        return list.stream().anyMatch(predicate);
    }


    /**
     * 测试集合内是否包含某个元素
     * @param list
     *  操作集合
     * @param predicate
     *  表达式 类似 x-&gt; x &gt; 10
     *
     *  @return
     * */
    public static <T> Boolean noOne(Collection<T> list, Predicate<T> predicate){
        return list.stream().noneMatch(predicate);
    }


    /**
     * 获取符合条件的元素集合
     * @param list
     *  操作集合
     * @param predicate
     *  表达式 类似 x-&gt; x &gt; 10
     *
     *  @return
     * */
    public static <T> Collection<T> everyFixTo(Collection<T> list, Predicate<T> predicate){
        return list.stream().filter(predicate).collect(Collectors.toList());
    }

    /**
     * 将集合内元素转换成其他元素
     * @param list
     *  操作集合
     * @param function
     *  表达式 类似 it -&gt; it.toLowerCase()
     *  @return
     * */
    public static <T,R> Collection<R> everyMapTo(Collection<T> list, Function<T,R> function){
        return list.stream().map(function).collect(Collectors.toList());
    }

    /**
     * 将集合内元素进行汇集操作
     * @param list
     *  操作集合
     * @param accumulator
     *  汇集算子
     *
     *  @return
     */
    public static <T,R> R everyReduceTo(Collection<T> list, BinaryOperator<R> accumulator){
        return list.stream().map(x->(R)x).reduce(accumulator).get();
    }

    /**
     * 将集合内元素映射并进行汇集操作
     * @param list
     *  操作集合
     * @param function
     *  映射操作
     * @param accumulator
     *  汇集算子
     *
     *  @return
     */
    public static <T,R> R everyMapAndReduceTo(Collection<T> list, Function<T,R> function, BinaryOperator<R> accumulator){
        return list.stream().map(function).reduce(accumulator).get();
    }


    /**
     * 将集合内元素过滤并映射
     * @param list
     *  操作集合
     * @param predicate
     *  过滤算子
     * @param function
     *  映射操作
     * @return
     *
     */
    public static <T,R> Collection<R> everyFixThenMapTo(Collection<T> list, Predicate<T> predicate,Function<T,R> function){
        return list.stream().filter(predicate).map(function).collect(Collectors.toList());
    }

    /**
     * 将集合内元素过滤并映射并汇集操作
     * @param list
     *  操作集合
     * @param predicate
     *  过滤算子
     * @param function
     *  映射操作
     * @param accumulator
     *  汇集算子
     *
     * @return
     *  映射结果
     */
    public static <T,R> R everyFixThenMapAndReduceTo(Collection<T> list, Predicate<T> predicate,Function<T,R> function, BinaryOperator<R> accumulator){
        return list.stream().filter(predicate).map(function).reduce(accumulator).get();
    }

    /**
     * 创建一个原始集合的反转集合
     * @param list 操作集合
     * @return 反转后的新集合
     * */
    public static <T> Collection<T>  reverse(Collection<T> list){
        if(list==null || list.size()==0) return list;
        int size = list.size();
        List<T> temps = new ArrayList<>(size);
        Object[] os = list.toArray();
        for(int i = 0;i < size;i++){
            temps.add((T)os[size-i-1]);
        }
        return temps;
    }

    /**
     * 将集合去重，并返回新集合
     * @param list 操作集合
     * @return 去重后集合
     * */
    public static <T> Collection<T> distinct(Collection<T> list){
        if(list==null || list.size()==0) return list;
        return list.stream().distinct().collect(Collectors.toList());
    }

    public static <T> Optional<T> findFirst(Collection<T> list,Predicate<T> predicate){
        return list.stream().filter(predicate).findFirst();
    }

    public static <T> void forEach(Collection<T> list, Consumer<T> action){
        list.stream().forEach(action);
    }

    public static <T extends Comparable<? super T>> T max(Collection<T> list){
        return max(list,Comparator.comparing(t->t));
    }

    public static <T> T max(Collection<T> list,Comparator<T> comparator){
        return list.stream().max(comparator).get();
    }

    public static <T,U extends Comparable<? super U>> T max(Collection<T> list,Function<? super T, ? extends U> keyExtractor){
        return max(list,Comparator.comparing(keyExtractor));
    }

    public static <T extends Comparable<? super T>> T min(Collection<T> list){
        return min(list,Comparator.comparing(t->t));
    }

    public static <T> T min(Collection<T> list,Comparator<T> comparator){
        return list.stream().min(comparator).get();
    }

    public static <T,U extends Comparable<? super U>> T min(Collection<T> list,Function<? super T, ? extends U> keyExtractor){
        return min(list,Comparator.comparing(keyExtractor));
    }

    public static <T> Collection<T> sorted(Collection<T> list){
        return list.stream().sorted().collect(Collectors.toList());
    }

    public static <T> Collection<T> sorted(Collection<T> list,Comparator<T> comparator){
        return list.stream().sorted(comparator).collect(Collectors.toList());
    }

    public static <T,U extends Comparable<? super U>> Collection<T> sortedByFunc(Collection<T> list,Function<? super T, ? extends U> keyExtractor){
        return list.stream().sorted(Comparator.comparing(keyExtractor)).collect(Collectors.toList());
    }
}
