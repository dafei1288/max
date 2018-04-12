package com.dafei1288.max;

import com.dafei1288.max.collect.TableStream;
import com.dafei1288.max.collect.TupleList;
import com.dafei1288.max.collect.Tuples;
import com.dafei1288.max.collect.tuple.Tuple;
import com.dafei1288.max.data.CsvLoader;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.*;


public class TestMath {
    public static TupleList tl;
    @BeforeClass
    public static void setUpBeforeClass() {
        try {
            //加载数据
            tl = CsvLoader.loadDataToTupleList("src/test/resources/test.csv",",",true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testZb(){
        //聚合数据
        TableStream tsAgg = tl.tableStream().aggregateMultiColumsByToStream(Arrays.asList(2),Arrays.asList(0));

        //连接后处理
        tl.tableStream().innerJoin(tsAgg,it->{return Objects.equals(((Tuple)it).get(0),((Tuple)it).get(3));}).select(it->{

            List<Object> temps = new ArrayList<>();
            temps.add(((Tuple)it).get(0));
            temps.add(((Tuple)it).get(1));
            temps.add(((Tuple)it).getDouble(2)/((Tuple)it).getDouble(4));
            return Tuples.tuple(temps.toArray());

        }).forEach(System.out::println);
    }

}
