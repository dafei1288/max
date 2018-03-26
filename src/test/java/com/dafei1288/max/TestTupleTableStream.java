package com.dafei1288.max;

import com.dafei1288.max.collect.TupleList;
import com.dafei1288.max.collect.TupleTableStream;
import com.dafei1288.max.collect.Tuples;
import com.dafei1288.max.collect.tuple.Tuple;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;

public class TestTupleTableStream {
    public static TupleTableStream from;
    public static TupleTableStream to;
    @BeforeClass
    public static void setUpBeforeClass() {
        TupleList tl = new TupleList();
        tl.add(Tuples.tuple(1,"张三",3000.00));
        tl.add(Tuples.tuple(2,"李四",2400.00));
        tl.add(Tuples.tuple(3,"王五",12200.00));
        tl.add(Tuples.tuple(4,"赵六",9000.00));
        tl.add(Tuples.tuple(5,"马七",21000.00));
        tl.add(Tuples.tuple(5,"马七",21000.00));

        from = new TupleTableStream(tl);
    }
    @Test
    public void testSelect(){

        from.select(it->{
            return ((Tuple)it).get(1);
        }).forEach(System.out::println);

    }


    @Test
    public void testAggregateBy(){

        from.aggregateBy(2,1).forEach((k,v)->System.out.println("key = "+k+" , value ="+v));

    }
    @Test
    public void testAggregateMultiColumsAndHavingBy(){
        from.aggregateMultiColumsBy(Arrays.asList(2),Arrays.asList(1)).forEach((k, v)->System.out.println("key = "+k+" , value ="+v));
        System.out.println();
        from.aggregateMultiColumsAndHavingBy(Arrays.asList(2),Arrays.asList(1),it->{return ((Tuple)((Map.Entry)it).getValue()).getDouble(0)>5000;}).forEach((k, v)->System.out.println("key = "+k+" , value ="+v));

    }
}
