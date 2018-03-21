package com.dafei1288.max.collect.tuple;

import com.dafei1288.max.collect.Tuples;

/**
 * 表示有1个元素的元组类型
 * 可迭代
 * 不可变，线程安全
 *
 */
public final class Tuple1<A> extends Tuple {

    public final A first;

    private Tuple1(final A first) {
        super(first);
        this.first = first;
    }

    /**
     * 创建一个包含1个元素的元组
     *
     * @param first 第一个元素
     * @param <A>   元素类型
     * @return 元组
     * @see Tuples#tuple(Object)
     */
    public static <A> Tuple1<A> with(final A first) {
        return new Tuple1<>(first);
    }

    /**
     * 反转元组
     *
     * @return 反转后的元组
     */
    @Override
    public Tuple1<A> swap() {
        return new Tuple1<>(this.first);
    }

    /**
     * 为元祖添加元素，返回新元组
     * @param e 元素
     * @param <E> 原型泛型
     *
     * @return 返回新元组
     *
     * */
    @Override
    public <E> Tuple addElement(E e) {
        return Tuple2.with(this.first,e);
    }
}