package com.dafei1288.max.lambda.function;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.function.Function;
import java.util.function.Predicate;

public class Functors {
    public static Function<String,String> UPPER_CASE_FUNCTOR = (x) -> x.toString().toUpperCase();
    public static Function<String,String> LOWER_CASE_FUNCTOR = (x) -> x.toString().toLowerCase();

//    public static Predicate<?> IS_NULL = (x) -> x!=null;
    public static <K,V> Object plus(K k,V v){
        if(k==null && v==null){
            return null;
        }else if(k==null){
            return v;
        }else if(v==null){
            return k;
        }
        if(k instanceof  String || v instanceof  String){
            return k.toString()+v.toString();
        }
        if(k instanceof Integer && v instanceof Integer){
            return (Integer)k+(Integer)v;
        }else{
            return NumberUtils.toDouble(k.toString())+NumberUtils.toDouble(v.toString());
        }

    }
}
