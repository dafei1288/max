package com.dafei1288.max.functor;

import com.google.common.base.Supplier;

public class SameNumberSupplier implements Supplier<Integer> {
    private Integer value;

    public SameNumberSupplier(Integer value){
       this.value = value;
    };

    public SameNumberSupplier(){
        this.value = 0;
    };

    public Integer get() {
//        this.value = this.value + 1;
        return this.value;
    }
}
