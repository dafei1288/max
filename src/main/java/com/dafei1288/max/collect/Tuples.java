package com.dafei1288.max.collect;

import com.dafei1288.max.collect.tuple.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

/**
 * 创建元组工具类，使用更加简便
 */
public final class Tuples {

    private Tuples() {
    }

    /**
     * 创建Tuple0
     *
     * @return Tuple0
     */
    public static Tuple0 tuple() {
        return Tuple0.with();
    }

    /**
     * 创建Tuple1
     *
     * @param first 元素
     * @param <A>   元素泛型
     * @return Tuple1
     */
    public static <A> Tuple1<A> tuple(final A first) {
        return Tuple1.with(first);
    }

    /**
     * 创建Tuple2
     *
     * @param first  元素
     * @param second 元素
     * @param <A>    元素泛型
     * @param <B>    元素泛型
     * @return Tuple2
     */
    public static <A, B> Tuple2<A, B> tuple(final A first, final B second) {
        return Tuple2.with(first, second);
    }

    /**
     * 创建Tuple3
     *
     * @param first  元素
     * @param second 元素
     * @param third  元素
     * @param <A>    元素泛型
     * @param <B>    元素泛型
     * @param <C>    元素泛型
     * @return Tuple3
     */
    public static <A, B, C> Tuple3<A, B, C> tuple(final A first, final B second, final C third) {
        return Tuple3.with(first, second, third);
    }

    /**
     * 创建Tuple4
     *
     * @param first  元素
     * @param second 元素
     * @param third  元素
     * @param fourth 元素
     * @param <A>    元素泛型
     * @param <B>    元素泛型
     * @param <C>    元素泛型
     * @param <D>    元素泛型
     * @return Tuple4
     */
    public static <A, B, C, D> Tuple4<A, B, C, D> tuple(final A first, final B second, final C third, final D fourth) {
        return Tuple4.with(first, second, third, fourth);
    }

    /**
     * 创建Tuple5
     *
     * @param first  元素
     * @param second 元素
     * @param third  元素
     * @param fourth 元素
     * @param fifth  元素
     * @param <A>    元素泛型
     * @param <B>    元素泛型
     * @param <C>    元素泛型
     * @param <D>    元素泛型
     * @param <E>    元素泛型
     * @return Tuple5
     */
    public static <A, B, C, D, E> Tuple5<A, B, C, D, E> tuple(final A first, final B second, final C third, final D fourth, final E fifth) {
        return Tuple5.with(first, second, third, fourth, fifth);
    }

    /**
     * 由数组创建TupleN
     *
     * @param args 数组
     * @return TupleN
     */
    public static TupleN tuple(final Object... args) {
        return TupleN.with(args);
    }


    /**
     * 由几个元组合并成新元组
     * @param tuples 元组组
     * @return 新元组
     * */
    public static Tuple combine(Tuple...tuples){
        List<Tuple> allList = Arrays.asList(tuples);
        List<Object> allObjects = allList.stream().map(it->it.toList()).flatMap(streams -> streams.stream()).collect(toList());
        int counts = allObjects.size();
        Tuple t = null;
        switch (counts){
            case 0: t = Tuples.tuple();break;
            case 1: t = Tuples.tuple(allObjects.get(0));break;
            case 2: t = Tuples.tuple(allObjects.get(0),allObjects.get(1));break;
            case 3: t = Tuples.tuple(allObjects.get(0),allObjects.get(1),allObjects.get(2));break;
            case 4: t = Tuples.tuple(allObjects.get(0),allObjects.get(1),allObjects.get(2),allObjects.get(3));break;
            case 5: t = Tuples.tuple(allObjects.get(0),allObjects.get(1),allObjects.get(2),allObjects.get(3),allObjects.get(4));break;
            default: t = Tuples.tuple(allObjects.toArray());break;
        }

        return t;
    }
    /**
     * 创建制定元素个数的空元组
     * @param size 元组个数
     * @return 元组
     * */
    public static Tuple createEmptyTuple(int size){
        Tuple t = null;
        Object first = null;
        Object second = null;
        switch (size){
            case 0: t = Tuples.tuple();break;
            case 1: t = Tuples.tuple(first);break;
            case 2: t = Tuples.tuple(first,second);break;
            case 3: t = Tuples.tuple(null,null,null);break;
            case 4: t = Tuples.tuple(null,null,null,null);break;
            case 5: t = Tuples.tuple(null,null,null,null,null);break;
            default: t = Tuples.tuple(new Object[size]);break;
        }
        return t;
    }

    /**
     * 元组列表针对其中某个元素排序，例如
     * <pre>{@code
     *     List<Tuple2> list = new ArrayList<>();
     *     list.add(Tuple2.with(5, "5"));
     *     list.add(Tuple2.with(2, "2"));
     *     list.add(Tuple2.with(3, "3"));
     *     list.add(Tuple2.with(1, "1"));
     *     list.add(Tuple2.with(4, "4"));
     *     //按第一列Integer类型升序
     *     Tuple.sort(list, 0, Integer::compare);
     *     //按第二列String类型升序
     *     Tuple.sort(list, 1, String::compareTo);
     *     }
     * </pre>
     *
     * @param list       需要排序的元组列表
     * @param index      用于排序的元素序号
     * @param comparator 排序函数
     * @param <T>        需要排序的数据类型
     */
    public static <T> void sort(final List<? extends Tuple> list, final int index, final Comparator<T> comparator) {
        requireNonNull(list, "list is null");
        if (list.size() < 2)
            return;
        requireNonNull(comparator, "comparator is null");
        if (index < 0)
            throw new IllegalArgumentException("index must >= 0");
        list.sort(Comparator.comparing(t -> t.get(index), comparator));
    }

    /**
     * 元组数组针对其中某个元素排序，例如
     * <pre>{@code
     *     Tuple2[] array = new Tuple2[5];
     *     array[0] = Tuple2.with("5", 5);
     *     array[1] = Tuple2.with("2", 2);
     *     array[2] = Tuple2.with("3", 3);
     *     array[3] = Tuple2.with("1", 1);
     *     array[4] = Tuple2.with("4", 4);
     *     //按第一列String类型升序
     *     Tuple.sort(array, 0, String::compareTo);
     *     //按第二列Integer类型升序
     *     Tuple.sort(array, 1, Integer::compare);
     *     }
     * </pre>
     *
     * @param array      需要排序的元组数组
     * @param index      用于排序的元素序号
     * @param comparator 排序函数
     * @param <T>        需要排序的数据类型
     */
    public static <T> void sort(final Tuple[] array, final int index, final Comparator<T> comparator) {
        requireNonNull(array, "array is null");
        if (array.length < 2)
            return;
        requireNonNull(comparator, "comparator is null");
        if (index < 0)
            throw new IllegalArgumentException("index must >= 0");
        Arrays.sort(array, Comparator.comparing(t -> t.get(index), comparator));
    }


}