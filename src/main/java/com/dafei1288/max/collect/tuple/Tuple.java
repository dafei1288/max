package com.dafei1288.max.collect.tuple;

import com.dafei1288.max.collect.Tuples;
import com.dafei1288.max.lambda.function.Functions;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.Serializable;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

/**
 * 元组类型
 * 用于表示多个不同类型的数据集合
 * 可迭代
 * 不可变，线程安全
 *
 */
public abstract class Tuple implements Iterable<Object>, Serializable {

    private final List<Object> valueList;

    Tuple(final Object... objects) {
        //其实就是简单的数组，只是包装成List，方便使用List的api进行元素操作
        this.valueList = Arrays.asList(objects);
    }

    public boolean isAllNumber(){
        return this.valueList.stream().allMatch(it->NumberUtils.isDigits(it.toString()));
    }

    public boolean hasNumber(){
        return this.valueList.stream().anyMatch(it->NumberUtils.isDigits(it.toString()));
    }

    public Optional<Double> maxToDouble(){
        return this.valueList.stream().filter(it->NumberUtils.isDigits(it.toString())).map(it->NumberUtils.createDouble(it.toString())).max(Comparator.naturalOrder());
    }

    public Optional<Double> minToDouble(){
        return this.valueList.stream().filter(it->NumberUtils.isDigits(it.toString())).map(it->NumberUtils.createDouble(it.toString())).min(Comparator.naturalOrder());
    }
    public Optional<Float> maxToFloat(){
        return this.valueList.stream().filter(it->NumberUtils.isDigits(it.toString())).map(it->NumberUtils.createFloat(it.toString())).max(Comparator.naturalOrder());
    }

    public Optional<Float> minToFloat(){
        return this.valueList.stream().filter(it->NumberUtils.isDigits(it.toString())).map(it->NumberUtils.createFloat(it.toString())).min(Comparator.naturalOrder());
    }
    public Optional<Integer> maxToInteger(){
        return this.valueList.stream().filter(it->NumberUtils.isDigits(it.toString())).map(it->NumberUtils.createInteger(it.toString())).max(Comparator.naturalOrder());
    }

    public Optional<Integer> minToInteger(){
        return this.valueList.stream().filter(it->NumberUtils.isDigits(it.toString())).map(it->NumberUtils.createInteger(it.toString())).min(Comparator.naturalOrder());
    }

    public Optional<Long> maxToLong(){
        return this.valueList.stream().filter(it->NumberUtils.isDigits(it.toString())).map(it->NumberUtils.createLong(it.toString())).max(Comparator.naturalOrder());
    }

    public Optional<Long> minToLong(){
        return this.valueList.stream().filter(it->NumberUtils.isDigits(it.toString())).map(it->NumberUtils.createLong(it.toString())).min(Comparator.naturalOrder());
    }
    /**
     * 将元组转换成列表
     *
     * @return 转换得到的列表
     */
    public final List<Object> toList() {
        return new ArrayList<>(this.valueList);
    }

    /**
     * 将元组转换成数组
     *
     * @return 转换得到的数组
     */
    public final Object[] toArray() {
        return this.valueList.toArray();
    }

    /**
     * 得到元组的大小
     *
     * @return 元组的大小
     */
    public final int size() {
        return this.valueList.size();
    }

    /**
     * 获取元组中指定位置的元素
     *
     * @param pos 元组中的位置
     * @param <T> 元素类型
     * @return 对应元素
     */
    @SuppressWarnings("unchecked")
    public final <T> T get(final int pos) {
        return (T) this.valueList.get(pos);
    }

    public final String getString(final int pos) {
        return this.valueList.get(pos).toString();
    }

    public final Integer getInteger(final int pos) {
        return Integer.valueOf(this.valueList.get(pos).toString());
    }

    public final Double getDouble(final int pos) {
        return Double.valueOf(this.valueList.get(pos).toString()) ;
    }


    public  final <T,R> R getByFunction(final int pos, Function<T,R> function){
        return function.apply((T)this.valueList.get(pos));
    }

    public final Tuple trans(Map<Integer,Function> transTable){
        List objs = IntStream.range(0,this.valueList.size()).boxed().map(index ->{
                    if(transTable.get(index)!=null){
                        return transTable.get(index).apply(this.valueList.get(index));
                    }else{
                        return this.valueList.get(index);
                    }
                }
        ).collect(Collectors.toList());
        return Tuples.tuple(objs.toArray());
    }


    public final Tuple transFilter(Map<Integer,Function> transTable){
        Object[] objs = transTable.keySet().stream().map(key->{
            return transTable.get(key).apply(this.valueList.get(key));
        }).toArray();
        return Tuples.tuple(objs);
    }

    /**
     * 判断元组中是否包含某元素
     *
     * @param value 需要判定的元素
     * @return 是否包含
     */
    public final boolean contains(final Object value) {
        return this.valueList.contains(value);
    }

    @Override
    public final Iterator<Object> iterator() {
        return this.valueList.iterator();
    }

    @Override
    public final Spliterator<Object> spliterator() {
        return this.valueList.spliterator();
    }

    /**
     * 将元组转成流
     *
     * @return 流
     */
    public final Stream<Object> stream() {
        return this.valueList.stream();
    }

    /**
     * 将元组转成并行流
     *
     * @return 流
     */
    public final Stream<Object> parallelStream() {
        return this.valueList.parallelStream();
    }

    /**
     * 迭代元组
     *
     * @param action 迭代函数
     */
    @Override
    public final void forEach(final Consumer<? super Object> action) {
        requireNonNull(action, "action is null");
        this.valueList.forEach(action);
    }

    /**
     * 带序号迭代元组
     *
     * @param action 带序号的迭代函数
     */
    public final void forEachWithIndex(final BiConsumer<Integer, ? super Object> action) {
        requireNonNull(action, "action is null");
        for (int i = 0, length = this.valueList.size(); i < length; i++)
            action.accept(i, this.valueList.get(i));
    }

    /**
     * 截取元组指定部分
     *
     * @param start 起始位置
     * @param end   终止位置
     * @return 截取得到的元组
     */
    public final Tuple subTuple(final int start, final int end) {
        if (start < 0 || end < 0)
            throw new IllegalArgumentException("start, end must >= 0");
        if (end >= this.valueList.size())
            throw new IllegalArgumentException("this tuple's size is" + this.valueList.size());
        int length = end - start + 1;
        if (length <= 0)
            throw new IllegalArgumentException("end must >= start");

        if (start == 0 && end == this.valueList.size() - 1)
            return this;

        switch (length) {
            case 1:
                return Tuple1.with(this.valueList.get(start));
            case 2:
                return Tuple2.with(this.valueList.get(start), this.valueList.get(end));
            case 3:
                return Tuple3.with(this.valueList.get(start), this.valueList.get(start + 1), this.valueList.get(end));
            case 4:
                return Tuple4.with(this.valueList.get(start), this.valueList.get(start + 1), this.valueList.get(start + 2), this.valueList.get(end));
            case 5:
                return Tuple5.with(this.valueList.get(start), this.valueList.get(start + 1), this.valueList.get(start + 2), this.valueList.get(start + 3), this.valueList.get(end));
            default:
                return TupleN.with(this.valueList.subList(start, end));
        }
    }

    /**
     * 于其他元组合并成新元组
     *
     * @param tuples 需要合并的元组
     * @return 合并后的新元组
     */
    public final Tuple add(final Tuple... tuples) {
        requireNonNull(tuples, "tuple is null");
        if (tuples.length == 0)
            return this;
        List<Object> temp = this.toList();
        for (Tuple t : tuples)
            temp.addAll(t.valueList);
        switch (temp.size()) {
            case 1:
                return Tuple1.with(temp.get(0));
            case 2:
                return Tuple2.with(temp.get(0),temp.get(1));
            case 3:
                return Tuple3.with(temp.get(0), temp.get(1), temp.get(2));
            case 4:
                return Tuple4.with(temp.get(0), temp.get(1), temp.get(2), temp.get(3));
            case 5:
                return Tuple5.with(temp.get(0), temp.get(1), temp.get(2), temp.get(3), temp.get(4));
            default:
                return TupleN.with(temp.toArray());
        }
    }


    /**
     * 将元组里的元素重复指定次数
     *
     * @param times 重复次数
     * @return 得到的元组
     */
    public final Tuple repeat(final int times) {
        if (times < 0)
            throw new IllegalArgumentException("times must >= 0");
        if (times == 0)
            return this;
        return TupleN.with(IntStream.range(0, times)
                .mapToObj(i -> this.valueList.toArray())
                .reduce((a, b) -> {
                    Object[] temp = new Object[a.length + b.length];
                    System.arraycopy(a, 0, temp, 0, a.length);
                    System.arraycopy(b, 0, temp, a.length, b.length);
                    return temp;
                }).get());
    }

    /**
     * 比较2个元组是否相同
     * 元组里的所有位置上的元素都相同，即为元组相同
     *
     * @param obj 需要比较的元组
     * @return 比较结果
     */
    @Override
    public final boolean equals(final Object obj) {
        if (isNull(obj))
            return false;
        if (obj == this)
            return true;
        if (obj instanceof Tuple)
            return ((Tuple) obj).valueList.equals(this.valueList);
        return false;
    }

    @Override
    public final int hashCode() {
        return this.valueList.hashCode();
    }

    /**
     * 得到元组的字符串，比如(123, 456)
     *
     * @return 元组的字符串
     */
    @Override
    public final String toString() {
        return this.valueList.stream()
                .map(Objects::toString)
                .collect(Collectors.joining(", ", "(", ")"));
    }

    /**
     * 反转元组
     * 反转后元组大小不变，子类各自实现可以达到最好性能，也可以指定返回值类型，方便使用
     *
     * @return 反转后的元组
     */
    public abstract Tuple swap();

    /**
     * 为元祖添加元素，返回新元组
     * @param e 元素
     * @param <E> 原型泛型
     *
     * @return 返回新元组
     *
     * */
    public abstract <E> Tuple addElement(E e);

    public <R> Tuple addElementByFunction(Function<Tuple,R> function){
        return addElement(function.apply(this));
    }

//    public static <T, A1, D1> Collector<T, Tuple1<A1>, Tuple1<D1>> collectors(
//            Collector<? super T, A1, D1> collector1
//    ) {
//        return Collector.<T, Tuple1<A1>, Tuple1<D1>>of(
//                () -> Tuples.tuple(
//                        collector1.supplier().get()
//                ),
//                (a, t) -> {
//                    collector1.accumulator().accept(a.get(0), t);
//                },
//                (a1, a2) -> Tuples.tuple(
//                        collector1.combiner().apply(a1.get(0), a2.get(1))
//                ),
//                a -> Tuples.tuple(
//                        collector1.finisher().apply(a.get(0))
//                )
//        );
//    }
//
//    public static <T, A1, A2, D1, D2>
//    Collector<T, Tuple2<A1, A2>, Tuple2<D1, D2>>
//    collectors(
//            Collector<T, A1, D1> collector1
//            , Collector<T, A2, D2> collector2
//    ) {
//        return Collector.of(
//                () -> Tuples.tuple(
//                        collector1.supplier().get()
//                        , collector2.supplier().get()
//                ),
//                (a, t) -> {
//                    collector1.accumulator().accept(a.get(0), t);
//                    collector2.accumulator().accept(a.get(1), t);
//                },
//                (a1, a2) -> Tuples.tuple(
//                        collector1.combiner().apply(a1.get(0), a2.get(0))
//                        , collector2.combiner().apply(a1.get(1), a2.get(1))
//                ),
//                a -> Tuples.tuple(
//                        collector1.finisher().apply(a.get(0))
//                        , collector2.finisher().apply(a.get(1))
//                )
//        );
//    }

//    public static <T, A1, A2,A3, D1, D2,D3>
//    Collector<T, Tuple3<A1, A2,A3>, Tuple3<D1, D2,D3>>
//    collectors(
//            Collector<T, A1, D1> collector1
//            , Collector<T, A2, D2> collector2
//            , Collector<T, A3, D3> collector3
//    ) {
//        return Collector.of(
//                () -> Tuples.tuple(
//                        collector1.supplier().get()
//                        , collector2.supplier().get()
//                        , collector3.supplier().get()
//                ),
//                (a, t) -> {
//                    collector1.accumulator().accept(a.get(0), t);
//                    collector2.accumulator().accept(a.get(1), t);
//                    collector3.accumulator().accept(a.get(2),t);
//                },
//                (a1, a2) -> Tuples.tuple(
//                        collector1.combiner().apply(a1.get(0), a2.get(0))
//                        , collector2.combiner().apply(a1.get(1), a2.get(1))
//                        , collector3.combiner().apply(a1.get(2), a2.get(2))
//                ),
//                a -> Tuples.tuple(
//                        collector1.finisher().apply(a.get(0))
//                        , collector2.finisher().apply(a.get(1))
//                        , collector3.finisher().apply(a.get(2))
//                )
//        );
//    }

    public static <T>
    Collector<T, Tuple, Tuple>
    collectors(
            List<Collector> collectors
    ) {
//        List<Collector> alc = Arrays.asList(collectors);
        return Collector.of(
                () -> Tuples.tuple(
                        collectors.stream().map(it->it.supplier().get()).collect(Collectors.toList()).toArray()
                ),
                (a, t) -> {
                    IntStream.range(0,collectors.size()).forEach(index->{
                        collectors.get(index).accumulator().accept(a.get(index),t);
                    });
                },
                (a1, a2) -> Tuples.tuple(
                        IntStream.range(0,collectors.size()).boxed().map(index->{
                            return collectors.get(index).combiner().apply(a1.get(index),a2.get(index));
                        }).collect(Collectors.toList()).toArray()
                ),
                a -> Tuples.tuple(
                        IntStream.range(0,collectors.size()).boxed().map(index->{
                            return collectors.get(index).finisher().apply(a.get(index));
                        }).collect(Collectors.toList()).toArray()
                )
        );
    }

}