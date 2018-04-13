package com.dafei1288.max;

import com.dafei1288.max.collect.Tuples;
import com.dafei1288.max.collect.tuple.Tuple;
import org.junit.Test;

public class TestTuple {
    @Test
    public void testAdd(){
        Tuple t = Tuples.tuple(1,2);
        Tuple t1 = t.addElementByFunction(it->{
            return it.getInteger(0)+it.getInteger(1)*12;
        });
        System.out.println(t1);

    }
}
