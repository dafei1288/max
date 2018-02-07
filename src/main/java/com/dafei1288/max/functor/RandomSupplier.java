package com.dafei1288.max.functor;

import com.google.common.base.Supplier;
import org.apache.commons.lang3.RandomUtils;

public class RandomSupplier{

    public static Supplier<Double> RANDOM_DOUBLE_SEED = ()->{return RandomUtils.nextDouble();};
    public static Supplier<Boolean> RANDOM_BOOLEAN_SEED = ()->{return RandomUtils.nextBoolean();};

}
