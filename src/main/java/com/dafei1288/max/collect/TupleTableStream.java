package com.dafei1288.max.collect;


import com.dafei1288.max.collect.tuple.Tuple;

import java.util.*;
import java.util.function.*;
import java.util.regex.Pattern;
import java.util.stream.*;

import static java.util.stream.Collectors.summingDouble;


public class  TupleTableStream<T extends Tuple> implements Stream<T> {
    private TupleList<T> delegate;

    public TupleTableStream(Stream<T> datas) {
        delegate = new TupleList<>();
        datas.forEach(it->this.delegate.add(it));
    }

    public  TupleTableStream(TupleList<T> tupleList){
        this.delegate = tupleList;
    }
    private void setDatas(TupleList<T> tupleList){
        this.delegate = tupleList;
    }
    @Override
    public Stream<T> filter(Predicate<? super T> predicate) {
        return delegate.stream().filter(predicate);
    }

    @Override
    public <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
        return delegate.stream().map(mapper);
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super T> mapper) {
        return delegate.stream().mapToInt(mapper);
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super T> mapper) {
        return delegate.stream().mapToLong(mapper);
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
        return delegate.stream().mapToDouble(mapper);
    }

    @Override
    public <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return delegate.stream().flatMap(mapper);
    }

    @Override
    public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
        return delegate.stream().flatMapToInt(mapper);
    }

    @Override
    public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
        return delegate.stream().flatMapToLong(mapper);
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
        return delegate.stream().flatMapToDouble(mapper);
    }

    @Override
    public Stream<T> distinct() {
        return delegate.stream().distinct();
    }

    @Override
    public Stream<T> sorted() {
        return delegate.stream().sorted();
    }

    @Override
    public Stream<T> sorted(Comparator<? super T> comparator) {
        return delegate.stream().sorted(comparator);
    }

    @Override
    public Stream<T> peek(Consumer<? super T> action) {
        return delegate.stream().peek(action);
    }

    @Override
    public Stream<T> limit(long maxSize) {
        return delegate.stream().limit(maxSize);
    }

    @Override
    public Stream<T> skip(long n) {
        return delegate.stream().skip(n);
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        delegate.stream().forEach(action);
    }

    @Override
    public void forEachOrdered(Consumer<? super T> action) {
        delegate.stream().forEachOrdered(action);
    }

    @Override
    public Object[] toArray() {
        return delegate.stream().toArray();
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        return (A[]) delegate.stream().toArray(generator);
    }

    @Override
    public T reduce(T identity, BinaryOperator<T> accumulator) {
        return (T) delegate.stream().reduce(identity,accumulator);
    }

    @Override
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        return delegate.stream().reduce(accumulator);
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner) {
        return (U) delegate.stream().reduce(identity,accumulator,combiner);
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
        return (R) delegate.stream().collect(supplier, accumulator, combiner);
    }

    @Override
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        return (R) delegate.stream().collect(collector);
    }

    @Override
    public Optional<T> min(Comparator<? super T> comparator) {
        return delegate.stream().min(comparator);
    }

    @Override
    public Optional<T> max(Comparator<? super T> comparator) {
        return delegate.stream().max(comparator);
    }

    @Override
    public long count() {
        return delegate.stream().count();
    }

    @Override
    public boolean anyMatch(Predicate<? super T> predicate) {
        return delegate.stream().anyMatch(predicate);
    }

    @Override
    public boolean allMatch(Predicate<? super T> predicate) {
        return delegate.stream().allMatch(predicate);
    }

    @Override
    public boolean noneMatch(Predicate<? super T> predicate) {
        return delegate.stream().noneMatch(predicate);
    }

    @Override
    public Optional<T> findFirst() {
        return delegate.stream().findFirst();
    }

    @Override
    public Optional<T> findAny() {
        return delegate.stream().findAny();
    }

    @Override
    public Iterator<T> iterator() {
        return delegate.stream().iterator();
    }

    @Override
    public Spliterator<T> spliterator() {
        return delegate.stream().spliterator();
    }

    @Override
    public boolean isParallel() {
        return delegate.stream().isParallel();
    }

    @Override
    public Stream<T> sequential() {
        return (Stream<T>) delegate.stream().sequential();
    }

    @Override
    public Stream<T> parallel() {
        return (Stream<T>) delegate.stream().parallel();
    }

    @Override
    public Stream<T> unordered() {
        return (Stream<T>) delegate.stream().unordered();
    }

    @Override
    public Stream<T> onClose(Runnable closeHandler) {
        return (Stream<T>) delegate.stream().onClose(closeHandler);
    }

    @Override
    public void close() {
        delegate.stream().close();
    }



    /**
     * 根据行号获取数据
     * @param i 行号
     * @return 行数据
     * */
    public Tuple getRow(int i){
        return (Tuple) this.delegate.get(i);
    }

    /**
     * 根据列索引获取列数据
     * @param i 列号
     * @param <E> 对象泛型
     * @return 列数据
     * */
    public <E> List<E> getColum(int i){
        return this.delegate.getListWithIndex(i);
    }

    /**
     * 获取行数据流
     * @param <T> 对象泛型 Tuple子类
     * @return 流
     * */
    public <T  extends Tuple> Stream<T> dataStream(){
        return this.delegate.stream();
    }


    /**
     * 全外连接
     * @param otherTable 外表
     * @return 连接后表
     * */
    public Stream<T> crossJoin(TupleTableStream<T> otherTable){
        return this.delegate.stream().flatMap(currentRow->otherTable.map(otherRow->Tuples.combine((Tuple)currentRow, otherRow)));
    }

    /**
     * 内连接
     * @param otherTable 外表
     * @param predicate 筛选器，匹配主外键对象
     * @return 连接后表
     * */
    public Stream<? super Tuple> innerJoin(TupleTableStream<T> otherTable, Predicate<Tuple> predicate){
        return this.delegate.stream().flatMap(currentRow->otherTable.dataStream().map(otherRow->Tuples.combine((Tuple) currentRow, otherRow))).filter(predicate);

    }
    /**
     * 左外连接
     * @param otherTable 外表
     * @param leftKeyIndex 主表主键索引
     * @param rightKeyIndex 外表主键索引
     * @return 连接后表
     * */
    public Stream<? super Tuple> leftOuterJoin(TupleTableStream<T> otherTable, int leftKeyIndex, int rightKeyIndex){
        int esize = otherTable.dataStream().findFirst().get().size();
        return this.dataStream().flatMap(currentRow->defaultIfEmpty(otherTable.dataStream().filter(otherRow->Objects.equals(currentRow.get(leftKeyIndex),otherRow.get(rightKeyIndex))),()->{return Tuples.createEmptyTuple(esize);}).map(otherRow->Tuples.combine(currentRow,otherRow)));

    }
    /**
     * 右外连接
     * @param otherTable 外表
     * @param leftKeyIndex 主表主键索引
     * @param rightKeyIndex 外表主键索引
     * @return 连接后表
     * */
    public Stream<? super Tuple> rightOuterJoin(TupleTableStream<T> otherTable, int leftKeyIndex, int rightKeyIndex){
        return otherTable.leftOuterJoin(this,rightKeyIndex,leftKeyIndex);
    }

    /**
     * 拼接两张数据表
     * @param otherTable 外表
     * @return 拼接后新表
     * */
    public Stream<? super Tuple> union(TupleTableStream<T> otherTable){
        return Stream.concat(this.dataStream(),otherTable.dataStream());
    }




    /**
     * 指定索引下的列包含指定集合里的数据内容
     * @param index 列索引
     * @param values 集合
     * @param <E> 指定类型
     * @return 新数据表
     * */
    public <E> Stream<? super Tuple> in(int index,Collection<E> values){
        return this.delegate.stream().filter(it-> values.contains(((Tuple)it).get(index)));
    }

    /**
     * 指定指是否出现在当前行内
     * @param value 集合
     * @param <E> 指定类型
     * @return 新数据表
     * */
    public <E> Stream<? super Tuple> inRow(E value){
        return this.delegate.stream().filter(it-> ((Tuple)it).contains(value));
    }

    /**
     * 指定索引下的列不包含指定集合里的数据内容
     * @param index 列索引
     * @param values 集合
     * @param <E> 指定类型
     * @return 新数据表
     * */
    public <E> Stream<? super Tuple> notIn(int index,Collection<E> values){
        return this.delegate.stream().filter(it->!values.contains(((Tuple)it).get(index)));
    }

    /**
     * 指定索引下的列匹配表达式项
     * @param index 列索引
     * @param regex 表达式
     * @return 新数据表
     * */
    public  Stream<? super Tuple> matches(int index,String regex){
        return this.delegate.stream().filter(it-> Pattern.matches(regex,((Tuple)it).get(index).toString()));
    }

    /**
     * 指定索引下的列不匹配表达式项
     * @param index 列索引
     * @param regex 表达式
     * @return 新数据表
     * */
    public  Stream<? super Tuple> notMatches(int index,String regex){
        return this.delegate.stream().filter(it->!Pattern.matches(regex,((Tuple)it).get(index).toString()));
    }

    /**
     * 指定索引下的列等于指定值
     * @param index 列索引
     * @param value 表达式
     * @param <E> 指定类型
     * @return 新数据表
     * */
    public <E> Stream<? super Tuple> eq(int index,E value){
        return this.delegate.stream().filter(it->Objects.equals(((Tuple)it).get(index),value));
    }

    /**
     * 当前列为空
     * @param index 列索引
     * @return 新数据表
     * */
    public Stream<? super Tuple> empty(int index){
        return this.delegate.stream().filter((it->Objects.isNull(((Tuple)it).get(index))));
    }

    /**
     * 当前列为空或空白
     * @param index 列索引
     * @return 新数据表
     * */
    public Stream<? super Tuple> emptyOrBlank(int index){
        return this.delegate.stream().filter(it-> Objects.isNull(((Tuple)it).get(index))||Objects.equals(((Tuple)it).get(index),""));
    }

    public <U> Stream<U> distinctColumBy(int index){
        return this.dataStream().map(it->(U)it.get(index)).distinct();
    }


    public long distinctCountColumBy(int index){
        return this.dataStream().map(it->it.get(index)).distinct().count();
    }

    public Stream<? super Tuple> sortedBy(int index){
        return this.dataStream().sorted(Comparator.comparing(it->{return it.get(index);}));
    }

    public Stream<? super Tuple> reversedBy(int index){
        return this.dataStream().sorted(Comparator.comparing(it->{return it.get(index);}));

    }


    public <E> Stream<? super Tuple> sortedBy(Function<? super Tuple,E> function,Comparator<? super E> comparator){
        return this.dataStream().sorted(Comparator.comparing(function,comparator));
    }

    public <E> Stream<? super Tuple> reversedBy(Function<? super Tuple,E> function,Comparator<? super E> comparator){
        return this.dataStream().sorted(Comparator.comparing(function,comparator));

    }

    /**
     * 截取记录
     * @param limit 截取记录条数
     * @return 新集合
     * */
    public Stream<? super Tuple> limit(int limit){
        return this.dataStream().limit(limit);
    }

    /**
     * 偏移数条记录后，截取记录
     * @param limit 截取记录条数
     * @param offset 偏移量
     * @return 新集合
     * */
    public Stream<? super Tuple> limit(int limit,int offset){
        return this.dataStream().skip(offset).limit(limit);
    }

    public <E> Stream<? super Tuple> select(Function<? super Tuple,E> function){
        return this.dataStream().map(function);
    }


    /**
     * 分组聚合
     * @param groupIndex 被分组列索引列表
     * @return 键值对索引
     * */
    public Map<Tuple,List<Tuple>> groupBy(Integer...groupIndex){
        return this.dataStream().collect(Collectors.groupingBy(it->{
            return Tuples.tuple(
                    Stream.of(groupIndex).map(index->it.get(index)).collect(Collectors.toList()).toArray()
            );
        }));
    }

    /**
     * 将表数据进行聚合，仅聚合列
     * @param aggColIndex 待聚合字段索引
     * @param groupIndex 被聚合字段列表
     * @return 新集合元素
     * */
    public Map<Tuple,Double> aggregateBy(Integer aggColIndex,Integer...groupIndex){
        return this.dataStream().collect(Collectors.groupingBy(it->{
            return Tuples.tuple(
                    Stream.of(groupIndex).map(index->it.get(index)).collect(Collectors.toList()).toArray()
            );
        },summingDouble(it->it.getDouble(aggColIndex))));
    }

    /**
     * 将表数据进行聚合，可聚合多列
     * @param aggIndexs 待聚合字段索引列表
     * @param groupIndexs 被聚合字段列表
     * @return 新集合元素
     * */
    public  Map<Tuple,Tuple> aggregateMultiColumsBy(List<Integer> aggIndexs,List<Integer> groupIndexs){
        return this.dataStream().collect(Collectors.groupingBy(it->{
                    return Tuples.tuple(
                            groupIndexs.stream().map(index-> it.get(index)).collect(Collectors.toList()).toArray()
                    );
                },Tuple.collectors(
                aggIndexs.stream().map(index->{
                    return Collectors.summingDouble(a -> ((Tuple)a).getDouble(index));
                }).collect(Collectors.toList())
                )
        ));
    }

    /**
     * 描述表
     * */
    public void discrib(){
        System.out.println("datas size : "+this.delegate.size());
        delegate.stream().forEach(System.out::println);
        System.out.println("**********discrib end*************");
    }

    private static <T extends Tuple> Stream<T> throwIfEmpty(Stream<T> stream) {
        Iterator<T> iterator = stream.iterator();
        if (iterator.hasNext()) {
            return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 0), false);
        } else {
            throw new NoSuchElementException("empty stream");
        }
    }

    private static <T  extends Tuple> Stream<T> defaultIfEmpty(Stream<T> stream, com.google.common.base.Supplier<T> supplier) {
        Iterator<T> iterator = stream.iterator();
        if (iterator.hasNext()) {
            return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 0), false);
        } else {
            return Stream.of(supplier.get());
        }
    }
}
