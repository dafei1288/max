package com.dafei1288.max.collect;


import com.dafei1288.max.collect.tuple.Tuple;
import com.google.common.base.Supplier;
import com.google.common.collect.Streams;
import com.google.common.collect.Table;


import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
/**
 * 元组列表
 * 利用元组实现列表
 * */
public class TupleTable {
    private HashMap<String,Tuple> cols;
    private TupleList<Tuple> datas;

    private void setDatas(TupleList<Tuple> datas){
        this.datas = datas;
    }

    public TupleTable(){
        cols = new LinkedHashMap<>();
        datas = new TupleList();
    }

    /**
     * 添加元数据
     * @param cols
     * @return
     * */
    @Deprecated public TupleTable addMetadata(HashMap<String,Tuple> cols){
        this.cols = cols;
        return this;
    }
    /**
     * 将对象封装成元素再添加到表内
     * @return this
     * */
    public TupleTable fillARow(Object...objs){
        Tuple t = Tuples.tuple(objs);
        datas.add(t);
        return this;
    }

    /**
     * 将元组添加到表内
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
     * @return 列数据
     * */
    public <E> List<E> getColum(int i){
        return this.datas.getListWithIndex(i);
    }
    /**
     * 获取行数据流
     * @param <T>
     * @return
     * */
    public <T  extends Tuple> Stream<T> dataStream(){
        return this.datas.stream();
    }
    /**
     * 根据列名获取列数据
     * @param colName 列名 ， 必须匹配元数据
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
        List<Tuple> ct = (List<Tuple>) this.datas.stream().flatMap(currentRow->otherTable.dataStream().map(otherRow->Tuples.combine((Tuple) currentRow,(Tuple)otherRow))).collect(Collectors.toList());
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
        List<Tuple> ct = (List<Tuple>) this.datas.stream().flatMap(currentRow->otherTable.dataStream().map(otherRow->Tuples.combine((Tuple) currentRow,(Tuple)otherRow))).filter(predicate).collect(Collectors.toList());
        TupleTable t = new TupleTable();
        TupleList<Tuple> tl = new TupleList<>();
        ct.forEach(it->tl.add(it));
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
        List<Tuple> ct = tt.collect(Collectors.toList());
        TupleTable t = new TupleTable();
        TupleList<Tuple> tl = new TupleList<>();
        ct.forEach(it->tl.add(it));
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
     *
     * @return
     * */
    public static TupleTable transTupleTable(TuplePivotTable tuplePivotTable){
        TupleTable tt = new TupleTable();

        tuplePivotTable.dataStream().forEach(it->{
            Table.Cell<Tuple,Tuple,Object> value = (Table.Cell<Tuple,Tuple,Object>)it;
            List<Object> temp = Streams.concat(value.getRowKey().stream(),value.getColumnKey().stream()).collect(Collectors.toList());
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
