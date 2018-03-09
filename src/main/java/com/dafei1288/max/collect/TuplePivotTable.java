package com.dafei1288.max.collect;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * 省 市 年 月 GDP
 * 天津市 河北区 2001 1 200
 * 天津市 红桥区 2001 2 199
 * 北京市 西城区 2002 3 211
 * 北京市 宣武区 2003 1 200
 * 上海市 虹桥区 2001 4 211
 *
 *
 *              2001      2002     2003
 *              1 2 3 4   1 2 3 4  1 2 3 4
 * 天津市 河北区
 * 天津市 红桥区
 * 北京市 西城区
 * 北京市 宣武区
 * 上海市 虹桥区
 * */
public class TuplePivotTable {

    Table<Tuple,Tuple,Object> datas;

    public TuplePivotTable(){
        datas = HashBasedTable.create();

    }

    public void addARow(Tuple x,Tuple y,Object value){
        this.datas.put(x,y,value);
    }

    public Stream dataStream(){
        return this.datas.cellSet().stream();
    }

    public void forEach(Consumer action){
        this.datas.cellSet().stream().forEach(action);
    }

    public Object getValue(Object...keys){
        Object[] xs = Arrays.copyOfRange(keys,0,this.dimXCount());
        Object[] ys = Arrays.copyOfRange(keys,this.dimXCount(),this.dimXCount()+this.dimYCount());
//        return null;
        Tuple xt = Tuples.tuple(xs);
        Tuple yt = Tuples.tuple(ys);

        System.out.println("x keys = "+xt);
        System.out.println("y keys = "+yt);


        return this.getValue(xt,yt);
    }

    public Object getValue(Tuple x,Tuple y){
        return this.datas.get(x,y);
    }


    public int dimXCount(){
        return this.datas.columnKeySet().stream().findFirst().get().size();
    }
    public int dimYCount(){
        return this.datas.rowKeySet().stream().findFirst().get().size();
    }

//    public <T,R,V> void addARow(Function<T,R> xTrans,Function<T,R> yTrans,Function<T,V> vTrans){
//        this.datas.put((T t)->xTrans.apply(t),(T t)->yTrans.apply(t),(T t)->vTrans.apply(t));
//    }
    public void discrib(){
        System.out.println("**********discrib start*************");
        Set<Tuple> ckeys = this.datas.columnKeySet();
        System.out.println("ckeys = "+ckeys.size());
        ckeys.stream().forEach(System.out::println);

        Set<Tuple> rkeys = this.datas.rowKeySet();
        System.out.println("rkeys = "+rkeys.size());
        rkeys.stream().forEach(System.out::println);


        Stream<Table.Cell<Tuple,Tuple,Object>> values = this.datas.cellSet().stream();
        values.forEach(System.out::println);
        System.out.println("**********discrib end*************");

    }


    public static <T extends Tuple,R extends Tuple,V> TuplePivotTable transPivotTable(TupleTable table,Function<T,R> xTrans,Function<T,R> yTrans,Function<T,V> vTrans){
        TuplePivotTable tpt = new TuplePivotTable();
        table.dataStream().forEach(it->{
            tpt.addARow(xTrans.apply((T)it),yTrans.apply((T)it),vTrans.apply((T)it));
        });
        return tpt;
    }

    public TupleTable toTupleTable(){
        TupleTable tt = new TupleTable();

        this.datas.cellSet().stream().forEach(it->{
            List<Object> temp = new ArrayList<>();

            List<Object> ykeys = it.getRowKey().toList();
            temp.addAll(ykeys);

            List<Object> xkeys = it.getColumnKey().toList();
            temp.addAll(xkeys);

            temp.add(it.getValue());

            tt.addARow(Tuples.tuple(temp.toArray()));
        });
        return tt;
    }
}
