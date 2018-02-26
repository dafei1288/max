package com.dafei1288.max.functor;

import com.google.common.base.Supplier;
import org.apache.commons.lang3.RandomUtils;

import java.util.function.Function;

public class RandomSupplier{

    public static Supplier<Double> RANDOM_DOUBLE_SEED = ()->{return RandomUtils.nextDouble();};
    public static Supplier<Boolean> RANDOM_BOOLEAN_SEED = ()->{return RandomUtils.nextBoolean();};
    public static Supplier<Integer> RANDOM_INT_SEED = ()->{return RandomUtils.nextInt();};
   // public static Integer RANDOM_INT_RANGE_SEED = (int x,int y)->{ return (Integer)RandomUtils.nextInt(x,y);};
}
