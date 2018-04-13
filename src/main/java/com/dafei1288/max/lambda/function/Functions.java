package com.dafei1288.max.lambda.function;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Functions {
    Function<String,String> UPPER_CASE_FUNCTOR = (x) -> x.toUpperCase();
    Function<String,String> LOWER_CASE_FUNCTOR = (x) -> x.toLowerCase();

    Function MYSELF = (x) -> x;

    BiFunction PLUS = (k,v)->{
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
    };

    interface MathFunc{
        Function<Integer,Integer> ABS2INT = (x) -> Math.abs(x);
        Function<Long,Long> ABS2LONG = (x) -> Math.abs(x);
        Function<Float,Float> ABS2FLOAT = (x) -> Math.abs(x);
        Function<Double,Double> ABS2DOUBLE = (x) -> Math.abs(x);



        Function<Double,Double> SIN = (x) -> Math.sin(x);
        Function<Double,Double> COS = (x) -> Math.cos(x);
        Function<Double,Double> TAN = (x) -> Math.tan(x);
        Function<Double,Double> LOG = (x) -> Math.log(x);

    }



}
