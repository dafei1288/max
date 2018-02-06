package com.dafei1288.max;



import com.dafei1288.max.functor.NaturalSupplier;


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

    public static Collection<Integer> plus(Collection<Integer> list,Integer value){
        return list.stream().map(it->it+value).collect(Collectors.toList());
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
