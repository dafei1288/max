package com.dafei1288.max;

import com.dafei1288.max.collect.Tuple;
import com.dafei1288.max.collect.TupleTable;
import com.dafei1288.max.collect.Tuples;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;

public class TestTupleTable {
    public static TupleTable tt;
    public static TupleTable t2;
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        tt = new TupleTable();


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

        t2 = new TupleTable();
        t2.addARow(Tuples.tuple(1,"11111",3000.00));
    }

    @Test
    public void testRightOuterJoin(){
        Tuple res = Tuples.tuple(1,"11111",3000.00,1,"张三",3000.00);
        TupleTable ttd= tt.rightOuterJoin(t2,0,0);
        ttd.discrib();

        assertEquals(ttd.getRow(0),res);
    }
}
