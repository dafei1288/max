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

    }
}
