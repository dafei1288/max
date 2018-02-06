package com.dafei1288.max;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class testMergeMap {
    public static void main(String[] args) {
        List<Integer> keys = Arrays.asList(10);
        List<String> values = Arrays.asList("USA", "Japan", "France", "Germany", "Italy", "U.K.","Canada");

        Map<Integer,String> map = CollectOperator.mergeToMap(keys,values,true);
        System.out.println(map);

        Collection<Integer> list = CollectOperator.createOnes(4);
        System.out.println(list);

        Collection<Integer> list1 = CollectOperator.createIntsWithRange(10);
        System.out.println(list1);

        Collection<Integer> list2 = CollectOperator.createIntsWithRange(0,2,10);
        System.out.println(list2);


        map = CollectOperator.mergeToIndexMap(values);
        System.out.println(map);

    }
}
