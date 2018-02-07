package com.dafei1288.max;

import com.dafei1288.max.functor.BaseOperator;
import com.dafei1288.max.functor.NaturalSupplier;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class CollectOperator {
    /**
     *  去掉空值，并全部转换成大写字母
     * */
    public static Collection<?> toUpperCaseExcludeNull(Collection<?> list){
        return toUpperCase(removeNullElement(list),"");
    }


    /**
     *  并全部转换成大写字母，空值以默认值代替
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
     * */
    public static Collection<?> toUpperCase(Collection<?> list){
        return toUpperCase(list,"");
    }

    /**
     * 将集合中的空值去掉
     * */
    public static Collection<?> removeNullElement(Collection<?> list){
        return list.stream().filter(it->it!=null).collect(Collectors.toList());
    }

    /**
     *  去掉空值，并全部转换成小写字母
     * */
    public static Collection<?> toLowerCaseExcludeNull(Collection<?> list){
        return toUpperCase(removeNullElement(list),"");
    }


    /**
     *  并全部转换成小写字母，空值以默认值代替
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
     * */
    public static <K,V> Map<K,V> mergeToMapWithAllKeys(Collection<K> key,Collection<V> value){
        return mergeToMap(key,value,true);
    }

    /**
     * 为集合创建自然数索引
     * */
    public static <V> Map<Integer,V> mergeToIndexMap(Collection<V> values){
        Collection<Integer> keys = createIntsWithRange(values.size());
        return mergeToMapWithAllKeys(keys,values);
    }

    /**
     * 创建自然数集合，从1开始，count个
     * @param count
     *  计数器
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
     * */
    public static Collection<Integer> createIntsWithRange(Integer from,Integer setp,Integer count){
        Stream<Integer> natural = Stream.generate(new NaturalSupplier(from,setp));
        return natural.limit(count).collect(Collectors.toList());
    }

    /**
     * 创建以0填充的集合，参数为个数
     * @param count
     *  计数器
     * */
    public static Collection<Integer> createZeros(Integer count){
        return createIntsWithRange(0,0,count);
    }

    /**
     * 创建以1填充的集合，参数为个数
     * @param count
     *  计数器
     * */
    public static Collection<Integer> createOnes(Integer count){
        return createIntsWithRange(1,0,count);
    }

    /**
     * 创建以默认值填充的集合，参数为个数
     * @param count
     *  计数器
     * @param value
     *  默认值
     * */
    public static Collection<Integer> createIntsWithDefaultValue(Integer count,Integer value){
        return createIntsWithRange(value,0,count);
    }
}
