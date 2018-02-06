package com.dafei1288.max;


import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;


public class CollectOperator {
    /**
     *  去掉空值，并全部转换成大写字母
     * */
    public static Collection<?> toUpperCaseExcludeNull(Collection<?> list){
        Collection<?> temp = list.stream().filter(it->it!=null).collect(Collectors.toList());
        return toUpperCase(temp,"");
    }


    /**
     *  并全部转换成大写字母，空值以默认值代替
     * */
    public static Collection<?> toUpperCase(Collection<?> list,Object defaultValue){
        return list
                .stream()
                .map(it->
                        {
                            Optional temp  = Optional.ofNullable(it);
                            return temp.orElseGet(()->defaultValue).toString().toUpperCase();
                        }
                )
                .collect(Collectors.toList());
    }

    /**
     *  并全部转换成大写字母，空值以空字符串代替
     * */
    public static Collection<?> toUpperCase(Collection<?> list){
        return toUpperCase(list,"");
    }


}
