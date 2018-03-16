package com.dafei1288.max.collect;


import com.google.common.base.Supplier;
import com.google.common.collect.Streams;
import com.google.common.collect.Table;

import javax.annotation.processing.SupportedSourceVersion;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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


    public TupleTable addMetadata(HashMap<String,Tuple> cols){
        this.cols = cols;
        return this;
    }

    public TupleTable fillARow(Object...objs){
        Tuple t = Tuples.tuple(objs);
        datas.add(t);
        return this;
    }
    public TupleTable addARow(Tuple tuple){
        datas.add(tuple);
        return this;
    }


    public Tuple getRow(int i){
        return (Tuple) this.datas.get(i);
    }

    public <E> List<E> getColum(int i){
        return this.datas.getListWithIndex(i);
    }

    public <T  extends Tuple> Stream<T> dataStream(){
        return this.datas.stream();
    }

    public <E> List<E> getColumByName(String colName){
        Tuple t = cols.get(colName);
        Integer colIndex = t.get(0);
        return this.datas.getListWithIndex(colIndex);
    }


    public TupleTable crossJoin(TupleTable otherTable){
        List<Tuple> ct = (List<Tuple>) this.datas.stream().flatMap(currentRow->otherTable.dataStream().map(otherRow->Tuples.combine((Tuple) currentRow,(Tuple)otherRow))).collect(Collectors.toList());
        TupleTable t = new TupleTable();
        TupleList<Tuple> tl = new TupleList<>();
        ct.forEach(it->tl.add(it));
        t.setDatas(tl);
        return t;
    }


    public TupleTable innerJoin(TupleTable otherTable, Predicate<Tuple> predicate){
        List<Tuple> ct = (List<Tuple>) this.datas.stream().flatMap(currentRow->otherTable.dataStream().map(otherRow->Tuples.combine((Tuple) currentRow,(Tuple)otherRow))).filter(predicate).collect(Collectors.toList());
        TupleTable t = new TupleTable();
        TupleList<Tuple> tl = new TupleList<>();
        ct.forEach(it->tl.add(it));
        t.setDatas(tl);
        return t;
    }

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

    public TupleTable rightOuterJoin(TupleTable otherTable,int leftKeyIndex,int rightKeyIndex){
        return otherTable.leftOuterJoin(this,rightKeyIndex,leftKeyIndex);
    }
    public void discrib(){
        System.out.println("**********discrib start*************");
        System.out.println("cols size : "+cols.size());
        cols.forEach((index,value)->{System.out.println("index = "+index+" , value = "+value);});

        System.out.println("datas size : "+datas.size());
        datas.stream().forEach(System.out::println);
        System.out.println("**********discrib end*************");
    }

    public <T extends Tuple,R extends Tuple,V> TuplePivotTable toTuplePivotTable(TupleTable table, Function<T,R> xTrans, Function<T,R> yTrans, Function<T,V> vTrans){
        TuplePivotTable tpt = new TuplePivotTable();
        this.dataStream().forEach(it->{
            tpt.addARow(xTrans.apply((T)it),yTrans.apply((T)it),vTrans.apply((T)it));
        });
        return tpt;
    }

    public static TupleTable transPivotTable(TuplePivotTable tuplePivotTable){
        TupleTable tt = new TupleTable();

        tuplePivotTable.dataStream().forEach(it->{
            Table.Cell<Tuple,Tuple,Object> value = (Table.Cell<Tuple,Tuple,Object>)it;
            List<Object> temp = Streams.concat(value.getRowKey().stream(),value.getColumnKey().stream()).collect(Collectors.toList());
            temp.add(value.getValue());
            tt.addARow(Tuples.tuple(temp.toArray()));
        });
        return tt;
    }

    static <T  extends Tuple> Stream<T> throwIfEmpty(Stream<T> stream) {
        Iterator<T> iterator = stream.iterator();
        if (iterator.hasNext()) {
            return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 0), false);
        } else {
            throw new NoSuchElementException("empty stream");
        }
    }

    static <T  extends Tuple> Stream<T> defaultIfEmpty(Stream<T> stream, Supplier<T> supplier) {
        Iterator<T> iterator = stream.iterator();
        if (iterator.hasNext()) {
            return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 0), false);
        } else {
            return Stream.of(supplier.get());
        }
    }

}
