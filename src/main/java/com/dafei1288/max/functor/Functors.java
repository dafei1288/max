package com.dafei1288.max.functor;

import java.util.function.Function;
import java.util.function.Predicate;

public class Functors {
    public static Function<String,String> UPPER_CASE_FUNCTOR = (x) -> x.toString().toUpperCase();
    public static Function<String,String> LOWER_CASE_FUNCTOR = (x) -> x.toString().toLowerCase();

//    public static Predicate<?> IS_NULL = (x) -> x!=null;
}
