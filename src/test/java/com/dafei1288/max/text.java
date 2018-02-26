package com.dafei1288.max;

import com.dafei1288.max.functor.Functors;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public class text {
    public static void main(String[] args) {
        List<String> G7 = Arrays.asList("USA", "Japan", "France", "Germany", "Italy", "U.K.","Canada");
        //String G7Countries = G7.stream().map(x -> x.toUpperCase()).collect(Collectors.joining(", "));
        CollectOperator.toUpperCase(G7).stream().forEach(System.out::println);


        List<Object> G71 = Arrays.asList("1111", 2222, "France", "Germany", null, "U.K.","Canada");
        //String G7Countries = G7.stream().map(x -> x.toUpperCase()).collect(Collectors.joining(", "));
        Collection<String> g11 = (Collection<String>) CollectOperator.toUpperCaseExcludeNull(G71);
        System.out.println(g11);


        G7.stream().map(Functors.LOWER_CASE_FUNCTOR).forEach(System.out::println);


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

        Collection<String> fixss = CollectOperator.everyFixTo(G7,it -> it.toString().contains("a")||it.toString().contains("A"));
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
}
