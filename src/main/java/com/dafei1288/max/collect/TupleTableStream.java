package com.dafei1288.max.collect;

import com.dafei1288.max.collect.tuple.Tuple;
import javafx.scene.control.Tab;

import java.util.*;
import java.util.function.*;
import java.util.regex.Pattern;
import java.util.stream.*;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;

public class TupleTableStream<T> implements TableStream<T> {
    private final Stream<? extends T> stream;

//    private final Supplier<TupleTableStream<T>> supplier;


    TupleTableStream(Stream<? extends T> stream){
        this.stream = stream.sequential();
//        this.supplier = ()-> (TupleTableStream<T>)this
    }



    public Stream<T> stream(){
        return (Stream<T>) this.stream;
    }

    @Override
    public Stream<T> filter(Predicate<? super T> predicate) {
        return TableStream.load(stream().filter(predicate));
    }

    @Override
    public TableStream<T> where(Predicate<? super T> predicate) {
        return TableStream.load(stream().filter(predicate));
    }

    @Override
    public <R> TableStream<R> map(Function<? super T, ? extends R> mapper) {
        return TableStream.load(stream().map(mapper));
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super T> mapper) {
        return stream().mapToInt(mapper);
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super T> mapper) {
        return stream().mapToLong(mapper);
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
        return stream().mapToDouble(mapper);
    }

    @Override
    public <R> TableStream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return TableStream.load(stream().flatMap(mapper));
    }

    @Override
    public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
        return stream().flatMapToInt(mapper);
    }

    @Override
    public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
        return stream().flatMapToLong(mapper);
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
        return stream().flatMapToDouble(mapper);
    }

    @Override
    public TableStream<T> distinct() {
        return TableStream.load(stream().distinct());
    }

    @Override
    public TableStream<T> sorted() {
        return TableStream.load(stream().sorted());
    }

    @Override
    public TableStream<T> sorted(Comparator<? super T> comparator) {
        return TableStream.load(stream().sorted(comparator));
    }

    @Override
    public TableStream<T> peek(Consumer<? super T> action) {
        return TableStream.load(stream().peek(action));
    }

    @Override
    public TableStream<T> limit(long maxSize) {
        return TableStream.load(stream().limit(maxSize));
    }

    @Override
    public TableStream<T> skip(long n) {
        return TableStream.load(stream().skip(n));
    }

    @Override
    public void forEachOrdered(Consumer<? super T> action) {
        this.stream.forEachOrdered(action);
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        this.stream().forEach(action);
    }
    @Override
    public Object[] toArray() {
        return this.stream().toArray();
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        return this.stream().toArray(generator);
    }

    @Override
    public T reduce(T identity, BinaryOperator<T> accumulator) {
        return this.stream().reduce(identity, accumulator);
    }

    @Override
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        return this.stream().reduce(accumulator);
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner) {
        return this.stream().reduce(identity, accumulator, combiner);
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
        return this.stream().collect(supplier, accumulator, combiner);
    }

    @Override
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        return this.stream().collect(collector);
    }

    @Override
    public Optional<T> min(Comparator<? super T> comparator) {
        return this.stream().min(comparator);
    }

    @Override
    public Optional<T> max(Comparator<? super T> comparator) {
        return this.stream().max(comparator);
    }

    @Override
    public long count() {
        return this.stream().count();
    }

    @Override
    public boolean anyMatch(Predicate<? super T> predicate) {
        return this.stream().anyMatch(predicate);
    }

    @Override
    public boolean allMatch(Predicate<? super T> predicate) {
        return this.stream().allMatch(predicate);
    }

    @Override
    public boolean noneMatch(Predicate<? super T> predicate) {
        return this.stream().noneMatch(predicate);
    }

    @Override
    public Optional<T> findFirst() {
        return this.stream().findFirst();
    }

    @Override
    public Optional<T> findAny() {
        return this.stream().findAny();
    }

    @Override
    public Iterator<T> iterator() {
        return this.stream().iterator();
    }

    @Override
    public Spliterator<T> spliterator() {
        return this.stream().spliterator();
    }

    @Override
    public boolean isParallel() {
        return this.stream().isParallel();
    }

    @Override
    public TableStream<T> sequential() {
        return TableStream.load(this.stream().sequential());
    }

    @Override
    public TableStream<T> parallel() {
        return TableStream.load(this.stream().parallel());
    }

    @Override
    public TableStream<T> unordered() {
        return TableStream.load(this.stream().unordered());
    }

    @Override
    public TableStream<T> onClose(Runnable closeHandler) {
        return TableStream.load(this.stream().onClose(closeHandler));
    }

    @Override
    public void close() {
        stream().close();
    }


    /**
     * 根据行号获取数据
     * @param i 行号
     * @return 行数据
     * */
    @Override
    public T getRow(int i){
        return (T)this.stream().skip(i).limit(1);
    }

    /**
     * 根据列索引获取列数据
     * @param i 列号
     * @param <E> 对象泛型
     * @return 列数据
     * */
    @Override
    public <E> List<E> getColum(int i){
        return this.stream().map(it->{
            if(it instanceof Tuple){
                return ((Tuple)it).get(i);
            }else{
                return (E)it;
            }
        }).collect(Collectors.toList());
    }



    /**
     * 全外连接
     * @param otherTable 外表
     * @return 连接后表
     * */
    @Override
    public TableStream<? extends Tuple> crossJoin(TableStream<? extends Tuple> otherTable){
        return TableStream.load(this.stream().flatMap(currentRow->otherTable.map(otherRow->Tuples.combine((Tuple)currentRow, otherRow))));

    }

    /**
     * 内连接
     * @param otherTable 外表
     * @param predicate 筛选器，匹配主外键对象
     * @return 连接后表
     * */
    @Override
    public TableStream<? extends Tuple> innerJoin(TableStream<? extends Tuple> otherTable, Predicate<Tuple> predicate){
        return TableStream.innerJoin((TableStream<? extends Tuple>) this,otherTable,predicate);
    }

//    public Stream<? extends Tuple> innerJoin1(TableStream<? extends Tuple> otherTable, Predicate<Tuple> predicate){
//        return
//                this.stream().flatMap(currentRow->otherTable.stream().map(otherRow->Tuples.combine((Tuple) currentRow, (Tuple)otherRow))).filter(predicate);
//                //.onClose(TableStreamUtils.closeAll(this,otherTable))
//
//    }

    /**
     * 左外连接
     * @param otherTable 外表
     * @param leftKeyIndex 主表主键索引
     * @param rightKeyIndex 外表主键索引
     * @return 连接后表
     * */
    @Override
    public TableStream<? extends Tuple> leftOuterJoin(TableStream<? extends Tuple> otherTable, int leftKeyIndex, int rightKeyIndex){
        int esize = otherTable.stream().findFirst().get().size();
        return TableStream.load(
                this.stream().flatMap(currentRow->
                        defaultIfEmpty(
                                otherTable.stream().filter(otherRow->Objects.equals(((Tuple)currentRow).get(leftKeyIndex),otherRow.get(rightKeyIndex))),()->{return Tuples.createEmptyTuple(esize);}
                        ).map(otherRow->Tuples.combine(((Tuple)currentRow),otherRow)))

        );
    }
    /**
     * 右外连接
     * @param otherTable 外表
     * @param leftKeyIndex 主表主键索引
     * @param rightKeyIndex 外表主键索引
     * @return 连接后表
     * */
    @Override
    public TableStream<? extends Tuple> rightOuterJoin(TableStream<? extends Tuple> otherTable, int leftKeyIndex, int rightKeyIndex){
        return otherTable.leftOuterJoin((TableStream<? extends Tuple>) this,rightKeyIndex,leftKeyIndex);

    }

    /**
     * 拼接两张数据表
     * @param otherTable 外表
     * @return 拼接后新表
     * */
    @Override
    public TableStream<? extends Tuple> union(TableStream<? extends Tuple> otherTable){
        Stream st = Stream.concat(this.stream(), otherTable.stream());
        return TableStream.load(st);
    }




    /**
     * 指定索引下的列包含指定集合里的数据内容
     * @param index 列索引
     * @param values 集合
     * @param <E> 指定类型
     * @return 新数据表
     * */
    @Override
    public <E> TableStream<T> in(int index, Collection<E> values){
        return TableStream.load(this.stream().filter(it-> values.contains(((Tuple)it).get(index))));

    }

    /**
     * 指定指是否出现在当前行内
     * @param value 集合
     * @param <E> 指定类型
     * @return 新数据表
     * */
    @Override
    public <E> TableStream<T> inRow(E value){
        return TableStream.load(
            this.stream().filter(it-> ((Tuple)it).contains(value))
        );
    }

    /**
     * 指定索引下的列不包含指定集合里的数据内容
     * @param index 列索引
     * @param values 集合
     * @param <E> 指定类型
     * @return 新数据表
     * */
    @Override
    public <E> TableStream<T> notIn(int index, Collection<E> values){
        return TableStream.load(
            this.stream().filter(it->!values.contains(((Tuple)it).get(index)))
        );
    }

    /**
     * 指定索引下的列匹配表达式项
     * @param index 列索引
     * @param regex 表达式
     * @return 新数据表
     * */
    @Override
    public TableStream<T> matches(int index, String regex){
        return TableStream.load(
            this.stream().filter(it-> Pattern.matches(regex,((Tuple)it).get(index).toString()))
        );
    }

    /**
     * 指定索引下的列不匹配表达式项
     * @param index 列索引
     * @param regex 表达式
     * @return 新数据表
     * */
    @Override
    public TableStream<T> notMatches(int index, String regex){
        return TableStream.load(
            this.stream().filter(it->!Pattern.matches(regex,((Tuple)it).get(index).toString()))
        );
    }

    /**
     * 指定索引下的列等于指定值
     * @param index 列索引
     * @param value 表达式
     * @param <E> 指定类型
     * @return 新数据表
     * */
    @Override
    public <E> TableStream<T> eq(int index, E value){
        return TableStream.load(
            this.stream().filter(it->Objects.equals(((Tuple)it).get(index),value))
        );
    }

    /**
     * 当前列为空
     * @param index 列索引
     * @return 新数据表
     * */
    @Override
    public TableStream<T> empty(int index){
        return TableStream.load(
            this.stream().filter((it->Objects.isNull(((Tuple)it).get(index))))
        );
    }

    /**
     * 当前列为空或空白
     * @param index 列索引
     * @return 新数据表
     * */
    @Override
    public TableStream<T> emptyOrBlank(int index){
        return TableStream.load(
            this.stream().filter(it-> Objects.isNull(((Tuple)it).get(index))||Objects.equals(((Tuple)it).get(index),""))
        );
    }
    @Override
    public <U> TableStream<U> distinctColumBy(int index){
        return TableStream.load(
                this.stream().map(
                        it->{
                            if(it instanceof Tuple){
                                return ((Tuple)it).get(index);
                            }else{
                                return (U)it;
                            }
                        }
                ).distinct()
        );
    }

    @Override
    public long distinctCountColumBy(int index){
        return this.stream().map(it->{
            if(it instanceof Tuple){
                return ((Tuple)it).get(index);
            }else{
                return it;
            }
        }).distinct().count();
    }
    @Override
    public  TableStream<T> sortedBy(int index){
        return TableStream.load(
                this.stream().sorted(Comparator.comparing(
                        it->{
                            if(it instanceof Tuple){
                                return ((Tuple)it).get(index);
                            }else{
                                return index;
                            }
                        }))
        );
    }
    @Override
    public TableStream<T> reversedBy(int index){
        return TableStream.load(
            this.stream().sorted(Comparator.comparing(it->{
                if(it instanceof Tuple){
                    return ((Tuple)it).get(index);
                }else{
                    return index;
                }
            }))
        );

    }

    @Override
    public <E> TableStream<T> sortedBy(Function<T, E> function, Comparator<E> comparator){
        return TableStream.load(
            this.stream().sorted(Comparator.comparing(function,comparator))
        );
    }
    @Override
    public <E> TableStream<T> reversedBy(Function<T, E> function, Comparator<E> comparator){
        return TableStream.load(
                this.stream().sorted(Comparator.comparing(function,comparator))
        );
    }

    /**
     * 截取记录
     * @param limit 截取记录条数
     * @return 新集合
     * */
    @Override
    public TableStream<T> limit(int limit){
        return TableStream.load(
            this.stream().limit(limit)
        );
    }

    /**
     * 偏移数条记录后，截取记录
     * @param limit 截取记录条数
     * @param offset 偏移量
     * @return 新集合
     * */
    @Override
    public TableStream<T> limit(int limit, int offset){
        return TableStream.load(
                this.stream().skip(offset).limit(limit)
        );
    }
    @Override
    public <R> TableStream<R> select(Function<T, R> mapper){
        return TableStream.load(
            this.stream().map(mapper)
        );
    }


    /**
     * 分组聚合
     * @param groupIndex 被分组列索引列表
     * @return 键值对索引
     * */
    @Override
    public  Map<? extends Tuple,List<T>> groupBy(Integer... groupIndex){
        return this.stream().collect(
                groupingBy(it->{
                    return Tuples.tuple(Stream.of(groupIndex).map(index->((Tuple)it).get(index)).collect(Collectors.toList()).toArray());
                }
            )
        );
    }

    /**
     * 将表数据进行聚合，仅聚合列
     * @param aggColIndex 待聚合字段索引
     * @param groupIndex 被聚合字段列表
     * @return 新集合元素
     * */
    @Override
    public Map<Tuple,Double> aggregateBy(Integer aggColIndex, Integer... groupIndex){
        return this.stream().collect(Collectors.groupingBy(it->{
            return Tuples.tuple(
                    Stream.of(groupIndex).map(index->((Tuple)it).get(index)).collect(Collectors.toList()).toArray()
            );
        },summingDouble(it->((Tuple)it).getDouble(aggColIndex))));
    }

    /**
     * 将表数据进行聚合，可聚合多列
     * @param aggIndexs 待聚合字段索引列表
     * @param groupIndexs 被聚合字段列表
     * @return 新集合元素
     * */
    @Override
    public  Map<Tuple,Tuple> aggregateMultiColumsBy(List<Integer> aggIndexs, List<Integer> groupIndexs){
        return this.stream().collect(Collectors.groupingBy(it->{
                    return Tuples.tuple(
                            groupIndexs.stream().map(index-> ((Tuple)it).get(index)).collect(Collectors.toList()).toArray()
                    );
                },Tuple.collectors(
                aggIndexs.stream().map(index->{
                    return Collectors.summingDouble(a -> ((Tuple)a).getDouble(index));
                }).collect(Collectors.toList())
                )
        ));
    }

    @Override
    public  Map<Tuple,Tuple> aggregateMultiColumsAndHavingBy(List<Integer> aggIndexs, List<Integer> groupIndexs, Predicate<Map.Entry<? extends Tuple, ? extends Tuple>> predicate){
        return aggregateMultiColumsBy(aggIndexs,groupIndexs).entrySet().stream().filter(predicate).collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
    }

    /**
     * 描述表
     * */
    @Override
    public void discrib(){
        System.out.println("datas size : "+this.stream().count());
        this.stream().forEach(System.out::println);
        System.out.println("**********discrib end*************");
    }

    @Override
    public TableStream<T> subTable(long from, long to) {
        return TableStream.load( this.stream().skip(from).limit(to));
    }




    private static  Stream<? extends Tuple> throwIfEmpty(Stream<? extends Tuple> stream) {
        Iterator iterator = stream.iterator();
        if (iterator.hasNext()) {
            return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 0), false);
        } else {
            throw new NoSuchElementException("empty stream");
        }
    }

    private static Stream<? extends Tuple> defaultIfEmpty(Stream<? extends Tuple> stream, com.google.common.base.Supplier<? extends Tuple> supplier) {
        Iterator iterator = stream.iterator();
        if (iterator.hasNext()) {
            return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 0), false);
        } else {
            return Stream.of(supplier.get());
        }
    }

}
