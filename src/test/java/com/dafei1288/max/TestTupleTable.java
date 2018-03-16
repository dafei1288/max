package com.dafei1288.max;

import com.dafei1288.max.collect.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

public class TestTupleTable {
    public static void main(String[] args) {
        TupleTable tt = new TupleTable();


        Tuple col1 = Tuples.tuple(0,"id","int");
        Tuple col2 = Tuples.tuple(1,"name","chart");
        Tuple col3 = Tuples.tuple(2,"salary","float");

        HashMap<String,Tuple> metadata = new LinkedHashMap<>();
        metadata.put(col1.get(1),col1);
        metadata.put(col2.get(1),col2);
        metadata.put(col3.get(1),col3);

        tt.addMetadata(metadata);

        Tuple r1 = Tuples.tuple(1,"张三",3000.00);
        Tuple r2 = Tuples.tuple(2,"李四",2400.00);
        Tuple r3 = Tuples.tuple(3,"王五",12200.00);
        Tuple r4 = Tuples.tuple(4,"赵六",9000.00);
        Tuple r5 = Tuples.tuple(5,"马七",21000.00);

        tt.addARow(r1);
        tt.addARow(r2);
        tt.addARow(r3);
        tt.addARow(r4);
        tt.addARow(r5);

        tt.discrib();

        List<String> name = tt.getColum(1);
        List<String> nameByName = tt.getColumByName("name");
        System.out.println(name);
        System.out.println(nameByName);

        Tuple rr2 = tt.getRow(2);
        System.out.println(rr2);

        System.out.println();
        TuplePivotTable tpt = TuplePivotTable.transPivotTable(tt,it->{return it.subTuple(0,0);},it->{return it.subTuple(1,1);},it->{return it.get(2);});
        tpt.discrib();
        tpt.forEach(System.out::println);

        System.out.println(tpt.getValue(1,"张三"));
        System.out.println(tpt.getValue(2,"张三"));

        tpt.toTupleTable().discrib();

        System.out.println(Tuples.combine(r1,r2));
        System.out.println(Tuples.combine(r1,r2,r3));

//        System.out.println(".........................");
//        TupleTable tta= tt.crossJoin(tt);
//        tta.discrib();
//
//        TupleTable ttb= tt.innerJoin(tt,it->it.get(0).equals(it.get(3)));
//        ttb.discrib();


        TupleTable t2 = new TupleTable();
        t2.addARow(Tuples.tuple(1,"11111",3000.00));
//        TupleTable ttc= tt.leftOuterJoin(t2,0,0);
//        ttc.discrib();
        TupleTable ttd= tt.rightOuterJoin(t2,0,0);
        ttd.discrib();
    }
}
