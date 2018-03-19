package com.dafei1288.max.collect;

/**
 * 表示有5个元素的元组类型
 * 可迭代
 * 不可变，线程安全
 *
 */
public final class Tuple5<A, B, C, D, E> extends Tuple {

    public final A first;
    public final B second;
    public final C third;
    public final D fourth;
    public final E fifth;

    private Tuple5(final A first, final B second, final C third, final D fourth, final E fifth) {
        super(first, second, third, fourth, fifth);
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
        this.fifth = fifth;
    }

    /**
     * 创建一个包含5个元素的元组
     *
     * @param first  第一个元素
     * @param second 第二个元素
     * @param third  第三个元素
     * @param fourth 第四个元素
     * @param fifth  第五个元素
     * @param <A>    第一个元素类型
     * @param <B>    第二个元素类型
     * @param <C>    第三个元素类型
     * @param <D>    第四个元素类型
     * @param <E>    第五个元素类型
     * @return 元组
     * @see Tuples#tuple(Object, Object, Object, Object, Object)
     */
    public static <A, B, C, D, E> Tuple5<A, B, C, D, E> with(final A first, final B second, final C third, final D fourth, final E fifth) {
        return new Tuple5<>(first, second, third, fourth, fifth);
    }

    /**
     * 反转元组
     *
     * @return 反转后的元组
     */
    @Override
    public Tuple5<E, D, C, B, A> swap() {
        return new Tuple5<>(this.fifth, this.fourth, this.third, this.second, this.first);
    }

    /**
     * 为元祖添加元素，返回新元组
     * @param e 元素
     * @param <E> 原型泛型
     *
     * @return
     *
     * */
    @Override
    public <E> Tuple addElement(E e) {
        return TupleN.with(this.first,this.second,this.third,this.fourth,this.fifth,e);
    }
}