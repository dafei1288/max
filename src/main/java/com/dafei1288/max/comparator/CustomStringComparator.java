package com.dafei1288.max.comparator;

import java.io.Serializable;
import java.text.ParseException;
import java.text.RuleBasedCollator;
import java.util.Comparator;
import java.util.function.Function;
/**
 * 自定义文本排序器
 * */
public class CustomStringComparator implements Comparator<String> {
    private static String myrule = "<赵<王<张<李";
    private static RuleBasedCollator myrulecollato ;
    static{
        try {
            myrulecollato = new RuleBasedCollator(myrule);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    @Override
    public int compare(String o1, String o2) {
        return myrulecollato.compare(o1, o2);
    }

    public static <T, U extends Comparable<? super U>> Comparator<T> comparing(
            Function<? super T, ? extends U> keyExtractor)
    {


        System.out.println("sorted rule is : "+myrulecollato.getRules());
        return (Comparator<T> & Serializable)
                    (c1, c2) -> myrulecollato.compare(keyExtractor.apply(c1),keyExtractor.apply(c2));

    }
}
