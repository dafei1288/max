package com.dafei1288.max;

import com.dafei1288.max.util.SimpleBloomFilter;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class TestSimpleBloomFilter {
    @Test
    public void testF(){
        String value  =   " stone2083@yahoo.cn " ;
        SimpleBloomFilter filter  =   new  SimpleBloomFilter();
        System.out.println(filter.contains(value));
        filter.add(value);
        System.out.println(filter.contains(value));
    }

    @Test
    public void testF2(){
        String value  =   "stone2083@yahoo.cn" ;
        List<String> strings = Arrays.asList("stone2083@yahoo.cn","dafei1288@sina.com","tt@t.tt");
        SimpleBloomFilter filter  =   new  SimpleBloomFilter();
        System.out.println(filter.contains(value));
        filter.add(strings);
        System.out.println(filter.contains(value));
        System.out.println(filter.contains("jack@sina.com"));
    }

    @Test
    public void testF3(){
        String value  =   "stone2083@yahoo.cn" ;
        List<String> strings = Arrays.asList("stone2083@yahoo.cn","dafei1288@sina.com","tt@t.tt");
        SimpleBloomFilter filter  =   new  SimpleBloomFilter(strings);
        System.out.println(filter.contains(value));
        filter.add(strings);
        System.out.println(filter.contains(value));
        System.out.println(filter.contains("jack@sina.com"));
    }
}
