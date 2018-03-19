package com.dafei1288.max.collect;

/**
 * 表示有3个元素的元组类型
 * 可迭代
 * 不可变，线程安全
 */
public final class Tuple3<A, B, C> extends Tuple {

    public final A first;
    public final B second;
    public final C third;

    private Tuple3(final A first, final B second, final C third) {
        super(first, second, third);
        this.first = first;
        this.second = second;
        this.third = third;
    }

    /**
     * 创建一个包含3个元素的元组
     *
     * @param first  第一个元素
     * @param second 第二个元素
     * @param third  第三个元素
     * @param <A>    第一个元素类型
     * @param <B>    第二个元素类型
     * @param <C>    第三个元素类型
     * @return 元组
     * @see Tuples#tuple(Object, Object, Object)
     */
    public static <A, B, C> Tuple3<A, B, C> with(final A first, final B second, final C third) {
        return new Tuple3<>(first, second, third);
    }

    /**
     * 反转元组
     *
     * @return 反转后的元组
     */
    @Override
    public Tuple3<C, B, A> swap() {
        return new Tuple3<>(this.third, this.second, this.first);
    }

    /**
     * 为元祖添加元素，返回新元组
     * @param
     * @param <E>
     *
     * @return
     *
     * */
    @Override
    public <E> Tuple addElement(E e) {
        return Tuple4.with(this.first,this.second,this.third,e);
    }
}