package com.dafei1288.max.collect;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Streams;
import com.google.common.collect.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 透视表
 *
 * <pre>
 *
 * 普通列表：
 *
 * 省 市 年 月 GDP
 * 天津市 河北区 2001 1 200
 * 天津市 红桥区 2001 2 199
 * 北京市 西城区 2002 3 211
 * 北京市 宣武区 2003 1 200
 * 上海市 虹桥区 2001 4 211
 *
 *
 * 转换成普通透视表：
 *
 *              2001      2002     2003
 *              1 2 3 4   1 2 3 4  1 2 3 4
 * 天津市 河北区
 * 天津市 红桥区
 * 北京市 西城区
 * 北京市 宣武区
 * 上海市 虹桥区
 *
 * </pre>
 * */
public class TuplePivotTable {

    Table<Tuple,Tuple,Object> datas;

    public TuplePivotTable(){
        datas = HashBasedTable.create();

    }
    /**
     * 添加列
     * @param x 行表头
     * @param y 列表头
     * @param value 值
     *
     * */
    public void addARow(Tuple x,Tuple y,Object value){
        this.datas.put(x,y,value);
    }


    /**
     * 获取数据对象流
     * @return
     * */
    public Stream dataStream(){
        return this.datas.cellSet().stream();
    }

    /**
     * 遍历数据对象
     * @param action
     * */
    public void forEach(Consumer action){
        this.datas.cellSet().stream().forEach(action);
    }


    /**
     * 获取值
     * 根据元数据自动截取对象，拼接成行标头及列表头
     *
     * @param keys 表头对象
     * @return
     * */
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
    /**
     * 获取值
     * @param x 行表头
     * @param y 列表头
     * @return
     * */
    public Object getValue(Tuple x,Tuple y){
        return this.datas.get(x,y);
    }

    /**
     * 列表头计数
     * @return
     * */
    public int dimXCount(){
        return this.datas.columnKeySet().stream().findFirst().get().size();
    }

    /**
     * 行表头计数
     * @return
     * */
    public int dimYCount(){
        return this.datas.rowKeySet().stream().findFirst().get().size();
    }

    /**
     * 描述表
     * */
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

    /**
     * 将列表转成透视表
     * @param table 列表
     * @param xTrans 行转行器
     * @param yTrans 列转换器
     * @param vTrans 值转换器
     * @param <T> tuple子类
     *
     * */
    public static <T extends Tuple,R extends Tuple,V> TuplePivotTable transPivotTable(TupleTable table,Function<T,R> xTrans,Function<T,R> yTrans,Function<T,V> vTrans){
        TuplePivotTable tpt = new TuplePivotTable();
        table.dataStream().forEach(it->{
            tpt.addARow(xTrans.apply((T)it),yTrans.apply((T)it),vTrans.apply((T)it));
        });
        return tpt;
    }

    /**
     * 将当前数据的透视表，转成默认列表
     *
     * 默认将行标头、列表头及数据串联
     *
     * @return
     * */
    public TupleTable toTupleTable(){
        TupleTable tt = new TupleTable();

        this.datas.cellSet().stream().forEach(it->{
            List<Object> temp = Streams.concat(it.getRowKey().stream(),it.getColumnKey().stream()).collect(Collectors.toList());
            temp.add(it.getValue());
            tt.addARow(Tuples.tuple(temp.toArray()));
        });
        return tt;
    }
}
