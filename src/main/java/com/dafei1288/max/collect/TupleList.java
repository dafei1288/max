package com.dafei1288.max.collect;

import com.dafei1288.max.collect.tuple.Tuple;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toCollection;


/**
 * Tuple的集合类
 * 用于存放多行tuple集合
 * */
public class TupleList<T extends Tuple> extends ArrayList {

    public TableStream<Tuple> tableStream(){
        return TableStream.load(this.stream());
    }

    public TupleList(){
        super();
    }

    public TupleList(int initialCapacity){
        super(initialCapacity);
    }

    /**
     * 根据索引获取元组内指定索引值得集合
     * @param i 索引
     * @param <E> 元素泛型
     * @return 列表
     * */
    public <E> List<E> getListWithIndex(int i){
        return (List<E>) this.stream().map(t->{return ((T)t).get(i);}).collect(Collectors.toList());
    }


    /**
     * 根据函数将元组值转换后形成列表
     * @param fun 函数
     * @param <E> 元素泛型
     * @return 列表
     * */
    public <E> List<E> getListWithFun(Function<T, E> fun){
        return (List<E>) this.stream().map(fun).collect(Collectors.toList());
    }


    public TupleList<T> reversed(){
        return IntStream.range(0, this.size()).mapToObj(i -> (T)this.get(this.size() - i - 1)).collect(toCollection(TupleList::new));

    }


    /**
     * 为元组列表内每个元组添加元素
     * @param lists 添加元素列
     * @param <E> 元素泛型
     *
     * @return 新元组
     *
     * */
    public <E> TupleList<T> addElements(List<E> lists) throws RuntimeException {
        if(lists==null || (this.size() > lists.size())){
            throw new RuntimeException("can't add element with different size.");
        }
        TupleList<T> tuples = new TupleList<>(this.size());
        IntStream.range(0,this.size()).forEach(index->{
            Tuple t = ((Tuple)this.get(index)).addElement(lists.get(index));
            tuples.add(t);
        });
        return tuples;
    }

    /**
     * 为元组列表内每个元组添加元素，不足数量以默认值填充
     * @param lists 添加元素列
     * @param defaultValue 默认元素
     * @param <E> 元素泛型
     *
     * @return 新元组
     *
     * */
    public <E> TupleList<T> addElements(List<E> lists, E defaultValue){
        if(lists==null || lists.size()==0){
            return this;
        }
        TupleList<T> tuples = new TupleList<>(this.size());
        IntStream.range(0,this.size()).forEach(index->{
            Tuple t;
            if(index < lists.size()){
                t = ((Tuple)this.get(index)).addElement(lists.get(index));
            }else{
                t = ((Tuple)this.get(index)).addElement(defaultValue);
            }
            tuples.add(t);
        });
        return tuples;
    }

    public TupleList subTupleList(int from , int to){
        TupleList tl = new TupleList();

        for(Object tuple: this.subList(from,to) ){
            tl.add(tuple);
        }
        return tl;
    }

    public TupleList head(int i){
        TupleList tl = new TupleList();
        for(Object tuple: this.subList(0,i) ){
            tl.add(tuple);
        }
        return tl;
    }
    public TupleList tail(int i){
        TupleList tl = new TupleList();
        for(Object tuple: this.subList(this.size()-i,this.size()) ){
            tl.add(tuple);
        }
        return tl;
    }

    public T get(final int x,final int y){
        return ((Tuple)this.get(x)).get(y);
    }
    public final String getString(final int x,final int y) {
        return ((Tuple)this.get(x)).get(y).toString();
    }

    public final Integer getInteger(final int x,final int y) {
        return Integer.valueOf(((Tuple)this.get(x)).get(y).toString());
    }
    public final Double getDouble(final int x,final int y) {
        return Double.valueOf(((Tuple)this.get(x)).get(y).toString()) ;
    }
}
