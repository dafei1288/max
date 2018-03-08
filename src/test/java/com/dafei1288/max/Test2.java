package com.dafei1288.max;

import com.dafei1288.max.collect.Tuple;
import com.dafei1288.max.collect.TupleList;
import com.dafei1288.max.collect.TupleN;
import com.dafei1288.max.collect.Tuples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Test2 {
    public static void main(String[] args) {
        List<Integer> list = IntStream.range(10,20).boxed().collect(Collectors.toList());
        list.stream().forEach(System.out::println);

        Collection<Integer> list1 = CollectOperator.createRangeInts(10,40);
        System.out.println(list1);

        list1 = CollectOperator.createRangeInts(10,40,5);
        System.out.println(list1);

        TupleList tl = new TupleList();
        tl.add(Tuples.tuple(1,"ab",3,4,5,6));
        tl.add(Tuples.tuple(2,"cd",3,4,5,6));
        tl.add(Tuples.tuple(3,"ef",3,4,5,6));
        tl.add(Tuples.tuple(4,"gk",3,4,5,6));
        tl.add(Tuples.tuple(5,"lm",3,4,5,6));

        List<String> t1 = tl.getListWithIndex(1);
        t1.stream().forEach(System.out::println);

        System.out.println();
        List<Integer> t2 = tl.getListWithIndex(0);
        t2.stream().forEach(System.out::println);

        System.out.println();
        List<Integer> t3 = tl.getListWithIndex(3);
        t3.stream().forEach(System.out::println);

        System.out.println();
        t1 = tl.getListWithFun(it->{return (String)(((Tuple)it).get(1));});
        t1.stream().forEach(System.out::println);

        System.out.println();
        tl.stream().forEach(System.out::println);

        System.out.println("........");
        List<String> addCol = new ArrayList<>();
        addCol.add("张三");
        addCol.add("李四");
        addCol.add("王五");
        addCol.add("赵六");
//        addCol.add("刘七");
        TupleList tlr = tl.addElements(addCol,"我");
        tlr.stream().forEach(System.out::println);

        List<String> t1l = tlr.getListWithIndex(6);
        System.out.println();
        t1l.stream().forEach(System.out::println);
    }

}
