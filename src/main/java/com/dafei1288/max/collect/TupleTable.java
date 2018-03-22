package com.dafei1288.max.collect;


import com.dafei1288.max.collect.tuple.Tuple;
import com.google.common.base.Supplier;
import com.google.common.collect.Streams;
import com.google.common.collect.Table;


import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.*;

/**
 * 元组列表
 * 利用元组实现列表
 * */
public class TupleTable {
    private HashMap<String,Tuple> cols;
    private TupleList<Tuple> datas;

    private TupleTable setDatas(TupleList<Tuple> datas){
        this.datas = datas;
        return this;
    }
    public TupleTable(TupleList<Tuple> datas){
        cols = new LinkedHashMap<>();
        this.datas = datas;
    }
    public TupleTable(){
        cols = new LinkedHashMap<>();
        datas = new TupleList();
    }

    /**
     * 添加元数据
     * @param cols 列原数据集合
     * @return 返回当前对象
     * */
    @Deprecated public TupleTable addMetadata(HashMap<String,Tuple> cols){
        this.cols = cols;
        return this;
    }
    /**
     * 将对象封装成元素再添加到表内
     * @param objs 对象数组
     * @return 返回当前对象
     * */
    public TupleTable fillARow(Object...objs){
        Tuple t = Tuples.tuple(objs);
        datas.add(t);
        return this;
    }

    /**
     * 将元组添加到表内
     * @param tuple 行元组
     * @return this
     * */
    public TupleTable addARow(Tuple tuple){
        datas.add(tuple);
        return this;
    }

    /**
     * 根据行号获取数据
     * @param i 行号
     * @return 行数据
     * */
    public Tuple getRow(int i){
        return (Tuple) this.datas.get(i);
    }
    /**
     * 根据列索引获取列数据
     * @param i 列号
     * @param <E> 对象泛型
     * @return 列数据
     * */
    public <E> List<E> getColum(int i){
        return this.datas.getListWithIndex(i);
    }
    /**
     * 获取行数据流
     * @param <T> 对象泛型 Tuple子类
     * @return 流
     * */
    public <T  extends Tuple> Stream<T> dataStream(){
        return this.datas.stream();
    }
    /**
     * 根据列名获取列数据
     * @param colName 列名 ， 必须匹配元数据
     * @param <E> 对象泛型
     * @return 列数据
     * */
    public <E> List<E> getColumByName(String colName){
        Tuple t = cols.get(colName);
        Integer colIndex = t.get(0);
        return this.datas.getListWithIndex(colIndex);
    }

    /**
     * 全外连接
     * @param otherTable 外表
     * @return 连接后表
     * */
    public TupleTable crossJoin(TupleTable otherTable){
        List<Tuple> ct = (List<Tuple>) this.datas.stream().flatMap(currentRow->otherTable.dataStream().map(otherRow->Tuples.combine((Tuple) currentRow, otherRow))).collect(toList());
        TupleTable t = new TupleTable();
        TupleList<Tuple> tl = new TupleList<>();
        ct.forEach(it->tl.add(it));
        t.setDatas(tl);
        return t;
    }

    /**
     * 内连接
     * @param otherTable 外表
     * @param predicate 筛选器，匹配主外键对象
     * @return 连接后表
     * */
    public TupleTable innerJoin(TupleTable otherTable, Predicate<Tuple> predicate){
        Stream<Tuple> st = this.datas.stream().flatMap(currentRow->otherTable.dataStream().map(otherRow->Tuples.combine((Tuple) currentRow, otherRow))).filter(predicate);
        TupleList<Tuple> tl = st.collect(toCollection(TupleList::new));
        TupleTable t = new TupleTable();
        t.setDatas(tl);
        return t;
    }
    /**
     * 左外连接
     * @param otherTable 外表
     * @param leftKeyIndex 主表主键索引
     * @param rightKeyIndex 外表主键索引
     * @return 连接后表
     * */
    public TupleTable leftOuterJoin(TupleTable otherTable,int leftKeyIndex,int rightKeyIndex){
        int esize = otherTable.dataStream().findFirst().get().size();
        Stream<Tuple> tt = this.dataStream().flatMap(currentRow->defaultIfEmpty(otherTable.dataStream().filter(otherRow->Objects.equals(currentRow.get(leftKeyIndex),otherRow.get(rightKeyIndex))),()->{return Tuples.createEmptyTuple(esize);}).map(otherRow->Tuples.combine(currentRow,otherRow)));
        TupleList<Tuple> tl = tt.collect(toCollection(TupleList::new));
        TupleTable t = new TupleTable();
        t.setDatas(tl);
        return t;
    }
    /**
     * 右外连接
     * @param otherTable 外表
     * @param leftKeyIndex 主表主键索引
     * @param rightKeyIndex 外表主键索引
     * @return 连接后表
     * */
    public TupleTable rightOuterJoin(TupleTable otherTable,int leftKeyIndex,int rightKeyIndex){
        return otherTable.leftOuterJoin(this,rightKeyIndex,leftKeyIndex);
    }

    /**
     * 拼接两张数据表
     * @param otherTable 外表
     * @return 拼接后新表
     * */
    public TupleTable union(TupleTable otherTable){
        TupleList tl =  Stream.concat(this.dataStream(),otherTable.dataStream()).collect(toCollection(TupleList::new));
        TupleTable temp = new TupleTable(tl);
        return temp;
    }


    /**
     * 过滤当前数据表
     * @param predicate 过滤条件
     * @return 新数据表
     * */
    public  TupleTable filter(Predicate<Tuple> predicate){
        TupleList tl =  this.dataStream().filter(predicate).collect(toCollection(TupleList::new));
        TupleTable temp = new TupleTable(tl);
        return temp;
    }

    /**
     * 指定索引下的列包含指定集合里的数据内容
     * @param index 列索引
     * @param values 集合
     * @param <E> 指定类型
     * @return 新数据表
     * */
    public <E> TupleTable in(int index,Collection<E> values){
        return filter(it-> values.contains(it.get(index)));
    }

    /**
     * 指定指是否出现在当前行内
     * @param value 集合
     * @param <E> 指定类型
     * @return 新数据表
     * */
    public <E> TupleTable inRow(E value){
        return filter(it-> it.contains(value));
    }

    /**
     * 指定索引下的列不包含指定集合里的数据内容
     * @param index 列索引
     * @param values 集合
     * @param <E> 指定类型
     * @return 新数据表
     * */
    public <E> TupleTable notIn(int index,Collection<E> values){
        return filter(it->!values.contains(it.get(index)));
    }

    /**
     * 指定索引下的列匹配表达式项
     * @param index 列索引
     * @param regex 表达式
     * @return 新数据表
     * */
    public  TupleTable matches(int index,String regex){
        return filter(it->Pattern.matches(regex,it.get(index).toString()));
    }

    /**
     * 指定索引下的列不匹配表达式项
     * @param index 列索引
     * @param regex 表达式
     * @return 新数据表
     * */
    public  TupleTable notMatches(int index,String regex){
        return filter(it->!Pattern.matches(regex,it.get(index).toString()));
    }

    /**
     * 指定索引下的列等于指定值
     * @param index 列索引
     * @param value 表达式
     * @param <E> 指定类型
     * @return 新数据表
     * */
    public <E> TupleTable eq(int index,E value){
        return filter(it->Objects.equals(it.get(index),value));
    }

    /**
     * 当前列为空
     * @param index 列索引
     * @return 新数据表
     * */
    public TupleTable empty(int index){
        return filter(it->Objects.isNull(it.get(index)));
    }

    /**
     * 当前列为空或空白
     * @param index 列索引
     * @return 新数据表
     * */
    public TupleTable emptyOrBlank(int index){
        return filter(it-> Objects.isNull(it.get(index))||Objects.equals(it.get(index),""));
    }

    public <U> List<U> distinctColumBy(int index){
        return this.dataStream().map(it->(U)it.get(index)).distinct().collect(toList());
    }


    public long distinctCountColumBy(int index){
        return this.dataStream().map(it->it.get(index)).distinct().count();
    }

    public TupleTable sortedBy(int index){
        TupleList<Tuple> tl = this.dataStream().sorted(Comparator.comparing(it->{return it.get(index);})).collect(toCollection(TupleList::new));
        TupleTable temp = new TupleTable(tl);
        return temp;
    }

    public TupleTable reversedBy(int index){
        TupleList<Tuple> tl = this.dataStream().sorted(Comparator.comparing(it->{return it.get(index);})).collect(toCollection(TupleList::new));
        TupleTable temp = new TupleTable(tl.reversed());
        return temp;
    }


    public <E> TupleTable sortedBy(Function<? super Tuple,E> function,Comparator<? super E> comparator){
        TupleList<Tuple> tl = this.dataStream().sorted(Comparator.comparing(function,comparator)).collect(toCollection(TupleList::new));
        TupleTable temp = new TupleTable(tl);
        return temp;
    }

    public <E> TupleTable reversedBy(Function<? super Tuple,E> function,Comparator<? super E> comparator){
        TupleList<Tuple> tl = this.dataStream().sorted(Comparator.comparing(function,comparator)).collect(toCollection(TupleList::new));
        TupleTable temp = new TupleTable(tl.reversed());
        return temp;
    }

    /**
     * 截取记录
     * @param limit 截取记录条数
     * @return 新集合
     * */
    public TupleTable limit(int limit){
        TupleList<Tuple> tl = this.dataStream().limit(limit).collect(toCollection(TupleList::new));
        TupleTable temp = new TupleTable(tl.reversed());
        return temp;
    }

    /**
     * 偏移数条记录后，截取记录
     * @param limit 截取记录条数
     * @param offset 偏移量
     * @return 新集合
     * */
    public TupleTable limit(int limit,int offset){
        TupleList<Tuple> tl = this.dataStream().skip(offset).limit(limit).collect(toCollection(TupleList::new));
        TupleTable temp = new TupleTable(tl.reversed());
        return temp;
    }

    public <E> TupleTable select(Function<? super Tuple,E> function){
        TupleList<Tuple> tl = this.dataStream().map(function).collect(toCollection(TupleList::new));
        TupleTable temp = new TupleTable(tl.reversed());
        return temp;
    }


    /**
     * 分组聚合
     * @param groupIndex 被分组列索引列表
     * @return 键值对索引
     * */
    public Map<Tuple,List<Tuple>> groupBy(Integer...groupIndex){
        return this.dataStream().collect(Collectors.groupingBy(it->{
            return Tuples.tuple(
                    Stream.of(groupIndex).map(index->it.get(index)).collect(Collectors.toList()).toArray()
            );
        }));
    }

    /**
     * 将表数据进行聚合，仅聚合列
     * @param aggColIndex 待聚合字段索引
     * @param groupIndex 被聚合字段列表
     * @return 新集合元素
     * */
    public Map<Tuple,Double> aggregateBy(Integer aggColIndex,Integer...groupIndex){
        return this.dataStream().collect(Collectors.groupingBy(it->{
            return Tuples.tuple(
                    Stream.of(groupIndex).map(index->it.get(index)).collect(Collectors.toList()).toArray()
            );
        },summingDouble(it->it.getDouble(aggColIndex))));
    }

    /**
     * 将表数据进行聚合，可聚合多列
     * @param aggIndexs 待聚合字段索引列表
     * @param groupIndexs 被聚合字段列表
     * @return 新集合元素
     * */
    public  Map<Tuple,Tuple> aggregateMultiColumsBy(List<Integer> aggIndexs,List<Integer> groupIndexs){
        return this.dataStream().collect(Collectors.groupingBy(it->{
                    return Tuples.tuple(
                            groupIndexs.stream().map(index-> it.get(index)).collect(Collectors.toList()).toArray()
                    );
                },Tuple.collectors(
                    aggIndexs.stream().map(index->{
                        return Collectors.summingDouble(a -> ((Tuple)a).getDouble(index));
                    }).collect(Collectors.toList())
                )
        ));
    }

    /**
     * 描述表
     * */
    public void discrib(){
        System.out.println("**********discrib start*************");
        System.out.println("cols size : "+cols.size());
        cols.forEach((index,value)->{System.out.println("index = "+index+" , value = "+value);});

        System.out.println("datas size : "+datas.size());
        datas.stream().forEach(System.out::println);
        System.out.println("**********discrib end*************");
    }

    /**
     * 将列表转成透视表
     * @param table 列表
     * @param xTrans 行转行器
     * @param yTrans 列转换器
     * @param vTrans 值转换器
     * @param <T> tuple子类
     * @param <R> tuple子类
     * @param <V> 值对象泛型
     * @return 返回透视表
     * */
    public <T extends Tuple,R extends Tuple,V> TuplePivotTable toTuplePivotTable(TupleTable table, Function<T,R> xTrans, Function<T,R> yTrans, Function<T,V> vTrans){
        TuplePivotTable tpt = new TuplePivotTable();
        this.dataStream().forEach(it->{
            tpt.addARow(xTrans.apply((T)it),yTrans.apply((T)it),vTrans.apply((T)it));
        });
        return tpt;
    }

    /**
     * 将透视表，转成默认列表
     *
     * 默认将行标头、列表头及数据串联
     * @param tuplePivotTable 透视表对象
     * @return 返回列表
     * */
    public static TupleTable transTupleTable(TuplePivotTable tuplePivotTable){
        TupleTable tt = new TupleTable();

        tuplePivotTable.dataStream().forEach(it->{
            Table.Cell<Tuple,Tuple,Object> value = (Table.Cell<Tuple,Tuple,Object>)it;
            List<Object> temp = Streams.concat(value.getRowKey().stream(),value.getColumnKey().stream()).collect(toList());
            temp.add(value.getValue());
            tt.addARow(Tuples.tuple(temp.toArray()));
        });
        return tt;
    }

   private static <T  extends Tuple> Stream<T> throwIfEmpty(Stream<T> stream) {
        Iterator<T> iterator = stream.iterator();
        if (iterator.hasNext()) {
            return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 0), false);
        } else {
            throw new NoSuchElementException("empty stream");
        }
    }

    private static <T  extends Tuple> Stream<T> defaultIfEmpty(Stream<T> stream, Supplier<T> supplier) {
        Iterator<T> iterator = stream.iterator();
        if (iterator.hasNext()) {
            return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 0), false);
        } else {
            return Stream.of(supplier.get());
        }
    }

}
