package com.dafei1288.max;

import com.dafei1288.max.collect.TableStream;
import com.dafei1288.max.collect.TupleList;
import com.dafei1288.max.collect.TupleTableStream;
import com.dafei1288.max.collect.Tuples;
import com.dafei1288.max.collect.tuple.Tuple;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public class TestTupleTableStream {
//    public static TableStream<Tuple> from;
//    public static TableStream<Tuple> to;
//    public static Supplier<TableStream<Tuple>> supplier;

    public static TupleList tl;

    @BeforeClass
    public static void setUpBeforeClass() {
        tl = new TupleList();
        tl.add(Tuples.tuple(1,"张三",3000.00));
        tl.add(Tuples.tuple(2,"李四",2400.00));
        tl.add(Tuples.tuple(3,"王五",12200.00));
        tl.add(Tuples.tuple(4,"赵六",9000.00));
        tl.add(Tuples.tuple(5,"马七",21000.00));
        tl.add(Tuples.tuple(5,"马七",21000.00));

//        from = TableStream.load(tl);
//        supplier = ()-> from;
    }
    @Test
    public void testSelect(){

        tl.tableStream().select(it->{
            return ((Tuple)it).get(1);
        }).forEach(System.out::println);

    }


    @Test
    public void testAggregateBy(){

        tl.tableStream().aggregateBy(2,1).forEach((k,v)->System.out.println("key = "+k+" , value ="+v));

    }
    @Test
    public void testAggregateMultiColumsAndHavingBy(){

        tl.tableStream().aggregateMultiColumsBy(Arrays.asList(2),Arrays.asList(1)).forEach((k, v)->System.out.println("key = "+k+" , value ="+v));
        System.out.println();
        tl.tableStream().aggregateMultiColumsAndHavingBy(Arrays.asList(2),Arrays.asList(1),it->{return ((Tuple)((Map.Entry)it).getValue()).getDouble(0)>5000;}).forEach((k, v)->System.out.println("key = "+k+" , value ="+v));
        System.out.println("......");
    }

    @Test
    public void testInnerJoin(){
        TupleList rightList = new TupleList();
        rightList.add(Tuples.tuple("0","死"));
        rightList.add(Tuples.tuple("1","活"));
        TableStream rightTable = rightList.tableStream();

        tl.tableStream().innerJoin(rightTable,it->{return Objects.equals(((Tuple)it).get(1),((Tuple)it).get(13));}).forEach(System.out::println);
    }

    @Test
    public void testUnion(){
        tl.tableStream().union(tl.tableStream()).forEach(System.out::println);
        long count = tl.tableStream().union(tl.tableStream()).count();
        System.out.println(count);
    }
}
