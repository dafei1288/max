package com.dafei1288.max;

import com.dafei1288.max.collect.tuple.Tuple;
import com.dafei1288.max.collect.TupleTable;
import com.dafei1288.max.collect.Tuples;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class TestTupleTable {
    public static TupleTable tt;
    public static TupleTable t2;
    @BeforeClass
    public static void setUpBeforeClass() {
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
        Tuple r6 = Tuples.tuple(6,"马七",21000.00);

        tt.addARow(r1);
        tt.addARow(r2);
        tt.addARow(r3);
        tt.addARow(r4);
        tt.addARow(r5);
        tt.addARow(r6);
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

    @Test
    public void testFileter(){
        System.out.println("......");
        TupleTable t = tt.filter(it-> Arrays.asList(1,2,3).contains(it.get(0)));
        t.discrib();

    }

    @Test
    public void testIn(){
        TupleTable t = tt.in(0,Arrays.asList(1,2,3,4));
        t.discrib();
    }

    @Test
    public void testNotIn(){
        TupleTable t = tt.notIn(0,Arrays.asList(1,2,3,4));
        t.discrib();
    }
    @Test
    public void testLike1(){
        TupleTable t = tt.matches(0,"\\d+");
        t.discrib();

        t = tt.matches(0,"1");
        t.discrib();

        t = tt.notMatches(0,"1");
        t.discrib();
    }
    @Test
    public void testEq(){
        TupleTable t = tt.eq(0,1);
        t.discrib();

        t = tt.eq(1,"张三");
        t.discrib();

        t = tt.eq(1,"aaa");
        t.discrib();

    }


    @Test
    public void testInRow(){
        TupleTable t = tt.inRow(1);
        t.discrib();


        t = tt.inRow("李四");
        t.discrib();
    }

    @Test
    public void testUnion(){
        TupleTable t = tt.union(tt);
        t.discrib();
    }

    @Test
    public void testDistinctColumByIndex(){
        List<Integer> t = tt.distinctColumBy(0);
        System.out.println(t);
        System.out.println(tt.distinctCountColumBy(0));

        List<String> ts = tt.distinctColumBy(1);
        System.out.println(ts);
        System.out.println(tt.distinctCountColumBy(1));
    }

    @Test
    public void testSort(){
        TupleTable t = tt.sortedBy(0);
        t.discrib();

        t = t.reversedBy(0);
        t.discrib();
    }
    @Test
    public void testSortCompartor(){
        TupleTable t = tt.sortedBy(it->it.getInteger(0),Comparator.naturalOrder());
        t.discrib();

        t = tt.reversedBy(it->it.getInteger(0),Comparator.naturalOrder());
        t.discrib();
    }

    @Test
    public void testLimitOffset(){
        TupleTable t = tt.limit(2);
        t.discrib();

        t = tt.limit(2,1);
        t.discrib();
    }

    @Test
    public void testSelect(){
        TupleTable t = tt.select(it->{
            return Tuples.tuple(it.getInteger(0)*10,it.getString(1)+111);
        });
        t.discrib();
    }
}
