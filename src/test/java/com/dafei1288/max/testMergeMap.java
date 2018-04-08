package com.dafei1288.max;

import com.dafei1288.max.collect.tuple.Tuple;
import com.dafei1288.max.collect.TupleList;
import com.dafei1288.max.collect.Tuples;
import com.dafei1288.max.lambda.function.Functions;
import com.dafei1288.max.lambda.CollectOperator;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class testMergeMap {
    @Test
    public void testFilter() {
        List<Integer> keys = Arrays.asList(10);
        List<String> values = Arrays.asList("USA", "Japan", "France", "Germany", "Italy", "U.K.","Canada");

        Map<Integer,String> map = CollectOperator.mergeToMap(keys,values,true);
        System.out.println(map);

        Collection<Integer> list = CollectOperator.createOnes(4);
        System.out.println(list);

        list = CollectOperator.createIntsWithDefaultValue(10,5);
        System.out.println(list);

        Collection<Integer> list1 = CollectOperator.createIntsWithRange(10);
        System.out.println(list1);

        Collection<Integer> list2 = CollectOperator.createIntsWithRange(0,2,10);
        System.out.println(list2);

        System.out.println(CollectOperator.plus(list2,10.0));
        Collection<Double> t = CollectOperator.plus(list2,-2);
        //t.stream().forEach(System.out::println);
        System.out.println(CollectOperator.plus(list2,-2));

        map = CollectOperator.mergeToIndexMap(values);
        System.out.println(map);

        t = CollectOperator.plus(CollectOperator.createIntsWithRange(10),CollectOperator.createIntsWithRange(0,2,15),true,1,1);
        System.out.println(t);

        t = CollectOperator.plus(CollectOperator.createIntsWithRange(10),CollectOperator.createIntsWithRange(0,2,15),false,1,1);
        System.out.println(t);

        //t.stream().forEach(System.out::println);

        t = CollectOperator.createRandoms(10);
        System.out.println(t);


        Collection<Integer> relist =CollectOperator.reverse(CollectOperator.createIntsWithRange(10));
        System.out.println(relist);

        Collection<String> revalues =CollectOperator.reverse(values);
        System.out.println(values);
        CollectOperator.forEach(revalues,System.out::println);

        String r = CollectOperator.max(revalues, Comparator.comparing(u->u));
        System.out.println(r);

        List<String> valueDistinct = Arrays.asList("USA", "USA", "France", "Germany", "USA", "U.K.","Canada");
        Collection<String> s = CollectOperator.distinct(valueDistinct);
        System.out.println(s);
    }


    @Test
    public void testStringFilter(){
        List<String> G7 = Arrays.asList("USA", "Japan", "France", "Germany", "Italy", "U.K.","Canada");
        //String G7Countries = G7.stream().map(x -> x.toUpperCase()).collect(Collectors.joining(", "));
        CollectOperator.toUpperCase(G7).stream().forEach(System.out::println);


        List<Object> G71 = Arrays.asList("1111", 2222, "France", "Germany", null, "U.K.","Canada");
        //String G7Countries = G7.stream().map(x -> x.toUpperCase()).collect(Collectors.joining(", "));
        Collection<String> g11 = (Collection<String>) CollectOperator.toUpperCaseExcludeNull(G71);
        System.out.println(g11);


        G7.stream().map(Functions.LOWER_CASE_FUNCTOR).forEach(System.out::println);


        Collection<Integer> evet = CollectOperator.createRandomInts(10);
        Boolean tag = CollectOperator.everyOne(evet,x -> x >= 10);
        System.out.println(evet);
        System.out.println(tag);

        System.out.println();

        evet = CollectOperator.createIntsWithRange(10);
        tag = CollectOperator.hasOne(evet,x -> x >= 11);
        System.out.println(evet);
        System.out.println(tag);


        System.out.println();

        evet = CollectOperator.createIntsWithRange(10);
        Collection<Integer> fixs = CollectOperator.everyFixTo(evet,x -> x >= 5);
        System.out.println(evet);
        System.out.println(fixs);

        Collection<String> fixss = CollectOperator.everyFixTo(G7,it -> it.contains("a")|| it.contains("A"));
        System.out.println(G7);
        System.out.println(fixss);

        fixss = CollectOperator.everyMapTo(G7,it -> it.toLowerCase());
        System.out.println(G7);
        System.out.println(fixss);

        String d = CollectOperator.everyMapAndReduceTo(evet,x->x.toString(),(sum,item)->sum + item);
        System.out.println(d);

        Integer di = CollectOperator.everyReduceTo(evet,(sum,item)->sum + item);
        System.out.println(di);

        di = CollectOperator.everyFixThenMapAndReduceTo(evet,x->x>5,x->x,(sum,item)->sum + item);
        System.out.println(di);

        Collection<Integer> fixs1 = CollectOperator.everyFixThenMapTo(evet,x->x>5,x->x+1);
        System.out.println(fixs1);
    }


    @Test
    public void testFilterNum(){
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
        t1 = tl.getListWithFun(it->{return ((Tuple)it).get(1);});
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

    @Test
    public void testMapToFrequencies(){
        List<String> addCol = new ArrayList<>();
        addCol.add("张三");
        addCol.add("李四");
        addCol.add("王五");
        addCol.add("赵六");
        addCol.add("赵六");
        addCol.add("赵六");
        addCol.add("赵六");
        CollectOperator.mapToFrequencies(addCol).entrySet().forEach(System.out::println);
    }
}
