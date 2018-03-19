package com.dafei1288.max.lambda.function;

import com.dafei1288.max.config.MaxConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.text.RuleBasedCollator;
import java.util.Comparator;
import java.util.function.Function;
/**
 * 姓名排序
 * 按百家姓排序
 * 也可以自行修改max.yaml里的属性 surnameComparatorRule 来控制排序顺序
 * */
public class SurnameComparator implements Comparator<String> {

    private static final Logger logger = LoggerFactory.getLogger(SurnameComparator.class);
    private static RuleBasedCollator myrulecollato ;

    static{
        try {
            myrulecollato = new RuleBasedCollator(MaxConfig.getInstance().getSurnameComparatorRuleComparatorRule());
        } catch (Exception e) {
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
        logger.info("sorted rule is : "+myrulecollato.getRules());
        return (Comparator<T> & Serializable)
                    (c1, c2) -> myrulecollato.compare(keyExtractor.apply(c1),keyExtractor.apply(c2));

    }
}
