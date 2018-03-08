package com.dafei1288.max.collect;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * Tuple的集合类
 * 用于存放多行tuple集合
 * */
public class TupleList<T extends Tuple> extends ArrayList {

    public TupleList(){
        super();
    }

    public TupleList(int initialCapacity){
        super(initialCapacity);
    }

    public <E> List<E> getListWithIndex(int i){
        return (List<E>) this.stream().map(t->{return (E)((T)t).get(i);}).collect(Collectors.toList());
    }

    public <E> List<E> getListWithFun(Function<T, E> fun){
        return (List<E>) this.stream().map(fun).collect(Collectors.toList());
    }

    public <E> TupleList<T> addElements(List<E> lists) throws RuntimeException {
        if(lists==null || (this.size() > lists.size())){
            throw new RuntimeException("can't add element with different size.");
        }
        TupleList<T> tuples = new TupleList<>(this.size());
        IntStream.range(0,this.size()).forEach(index->{
            Tuple t = ((Tuple)this.get(index)).addElement(lists.get(index));
//            System.out.println("add ==> " +lists.get(index)+" to "+ t );
            tuples.add(t);
        });
        return tuples;
    }

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
}
