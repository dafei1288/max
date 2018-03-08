package com.dafei1288.max.collect;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class TupleTable {
    private HashMap<String,Tuple> cols;
    private TupleList<Tuple> datas;

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

    public <E> List<E> getColumByName(String colName){
        Tuple t = cols.get(colName);
        Integer colIndex = t.get(0);
        return this.datas.getListWithIndex(colIndex);
    }

    public void discrib(){
        System.out.println("**********discrib start*************");
        System.out.println("cols size : "+cols.size());
        cols.forEach((index,value)->{System.out.println("index = "+index+" , value = "+value);});

        System.out.println("datas size : "+datas.size());
        datas.stream().forEach(System.out::println);
        System.out.println("**********discrib end*************");
    }
}
