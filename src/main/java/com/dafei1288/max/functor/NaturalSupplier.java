package com.dafei1288.max.functor;

import com.google.common.base.Supplier;

public class NaturalSupplier implements Supplier<Integer> {

    private Integer value = 0;
    private Integer setp = 1;

    public void setValue(Integer value){
        this.value = value;
    }

    public NaturalSupplier(Integer value){
        this.value = value;
    };
    public NaturalSupplier(Integer value,Integer setp){
        this.value = value;
        this.setp = setp;
    };

    public Integer getValue() {
        return value;
    }

    public Integer getSetp() {
        return setp;
    }

    public void setSetp(Integer setp) {
        this.setp = setp;
    }

    public NaturalSupplier(){
        this.value = 0;
    };
    public Integer get() {
        this.value = this.value + this.setp;
        return this.value;
    }
}
