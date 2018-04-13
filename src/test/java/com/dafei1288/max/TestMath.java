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

    @Test
    public  void testAddItemByFunction(){
        tl.tableStream().addCalculateItem(it->{return ((Tuple)it).getInteger(0)+ ((Tuple)it).getInteger(1);}).forEach(System.out::println);
    }

    @Test
    public void testNewZhanbi(){
        //聚合数据
        TableStream tsAgg = tl.tableStream().aggregateMultiColumsByToStream(Arrays.asList(2),Arrays.asList(0));
        //(2002, 498.0)
        //(2001, 448.0)


        //连接后处理
        tl.tableStream().innerJoin(tsAgg,
                //内连接条件
                it->{return Objects.equals(((Tuple)it).get(0),((Tuple)it).get(3));}

                )

                //添加占比计算元素
                .addCalculateItem(

                //占比计算公式
                it->{return ((Tuple)it).getDouble(2)/((Tuple)it).getDouble(4);}

                ).forEach(System.out::println);
//        (2001, 1, 100, 2001, 448.0, 0.22321428571428573)
//        (2001, 2, 101, 2001, 448.0, 0.22544642857142858)
//        (2001, 3, 103, 2001, 448.0, 0.22991071428571427)
//        (2001, 4, 144, 2001, 448.0, 0.32142857142857145)
//        (2002, 1, 110, 2002, 498.0, 0.22088353413654618)
//        (2002, 2, 111, 2002, 498.0, 0.22289156626506024)
//        (2002, 3, 113, 2002, 498.0, 0.22690763052208834)
//        (2002, 4, 164, 2002, 498.0, 0.3293172690763052)
    }
}
