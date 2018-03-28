package com.dafei1288.max.collect;
import com.dafei1288.max.collect.tuple.Tuple;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static java.util.Spliterator.ORDERED;
import static java.util.Spliterators.spliteratorUnknownSize;

public interface TableStream<T> extends Stream<T>, Iterable<T> {

    Stream<T> stream();

    TableStream<T> where(Predicate<? super T> predicate);

    @Override
    void forEach(Consumer<? super T> action);

    @Override
    Spliterator<T> spliterator() ;


    static <T> TableStream<T> load(Stream<? extends T> stream){
        if(stream==null) return TableStream.empty();
        if(stream instanceof TableStream){
            return (TableStream<T>) stream;
        }
        return new TupleTableStream<T>(stream);
    }


    static <T> TableStream<T> empty(){
        return load(Stream.empty());
    }

    static TableStream<Void> generate() {
        return generate(() -> null);
    }

    static <T> TableStream<T> generate(T value) {
        return generate(() -> value);
    }


    static <T> TableStream<T> generate(Supplier<? extends T> s) {
        return load(Stream.generate(s));
    }


    static <T> TableStream<T> load(T[] values, int startIndex, int endIndex) {
        return load(Arrays.asList(values).subList(startIndex, endIndex));
    }

    static <T> TableStream<T> load(TableStream<? extends T> stream) {
        if (stream == null)
            return TableStream.empty();

        return (TableStream<T>) stream;
    }

    static TableStream<Integer> load(IntStream stream) {
        if (stream == null)
            return TableStream.empty();

        return new TupleTableStream<>(stream.boxed());
    }


    static TableStream<Long> load(LongStream stream) {
        if (stream == null)
            return TableStream.empty();

        return new TupleTableStream<>(stream.boxed());
    }


    static TableStream<Double> load(DoubleStream stream) {
        if (stream == null)
            return TableStream.empty();

        return new TupleTableStream<>(stream.boxed());
    }


    static <T> TableStream<T> load(Iterable<? extends T> iterable) {
        if (iterable == null)
            return TableStream.empty();

        return load(iterable.iterator());
    }


    static <T> TableStream<T> load(Iterator<? extends T> iterator) {
        if (iterator == null)
            return TableStream.empty();

        return load(spliteratorUnknownSize(iterator, ORDERED));
    }

    static <T> TableStream<T> load(Enumeration<T> enumeration) {
        if (enumeration == null)
            return TableStream.empty();

        return TableStream.load(new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return enumeration.hasMoreElements();
            }

            @Override
            public T next() {
                return enumeration.nextElement();
            }
        });
    }


    static <T> TableStream<T> load(Spliterator<? extends T> spliterator) {
        if (spliterator == null)
            return TableStream.empty();

        return load(StreamSupport.stream(spliterator, false));
    }

    static <T> TableStream<T> of(T t){
        return load(Stream.of(t));
    }

    static <T> TableStream<T> of(T...values){
        return load(Stream.of(values));
    }


    T getRow(int i);

    <E> List<E> getColum(int i);

    TableStream<? extends Tuple> crossJoin(TableStream<? extends Tuple> otherTable);

    TableStream<? extends Tuple> innerJoin(TableStream<? extends Tuple> otherTable, Predicate<Tuple> predicate);

    TableStream<? extends Tuple> leftOuterJoin(TableStream<? extends Tuple> otherTable, int leftKeyIndex, int rightKeyIndex);

    TableStream<? extends Tuple> rightOuterJoin(TableStream<? extends Tuple> otherTable, int leftKeyIndex, int rightKeyIndex);

    TableStream<? extends Tuple> union(TableStream<? extends Tuple> otherTable);

    <E> TableStream<T> in(int index, Collection<E> values);

    <E> TableStream<T> inRow(E value);

    <E> TableStream<T> notIn(int index, Collection<E> values);

    TableStream<T> matches(int index, String regex);

    TableStream<T> notMatches(int index, String regex);

    <E> TableStream<T> eq(int index, E value);

    TableStream<T> empty(int index);

    TableStream<T> emptyOrBlank(int index);

    <U> TableStream<U> distinctColumBy(int index);

    long distinctCountColumBy(int index);

    TableStream<T> sortedBy(int index);

    TableStream<T> reversedBy(int index);

    <E> TableStream<T> sortedBy(Function<T, E> function, Comparator<E> comparator);

    <E> TableStream<T> reversedBy(Function<T, E> function, Comparator<E> comparator);

    TableStream<T> limit(int limit);

    TableStream<T> limit(int limit, int offset);

    <R> TableStream<R> select(Function<T, R> mapper);

    Map<? extends Tuple,List<T>> groupBy(Integer... groupIndex);

    Map<Tuple,Double> aggregateBy(Integer aggColIndex, Integer... groupIndex);

    Map<Tuple,Tuple> aggregateMultiColumsBy(List<Integer> aggIndexs, List<Integer> groupIndexs);

    Map<Tuple,Tuple> aggregateMultiColumsAndHavingBy(List<Integer> aggIndexs, List<Integer> groupIndexs, Predicate<Map.Entry<? extends Tuple, ? extends Tuple>> predicate);

    void discrib();

    TableStream<T> subTable(long from , long to);



    static TableStream<? extends Tuple> crossJoin(TableStream<? extends Tuple> leftTable,TableStream<? extends Tuple> rightTable){
        List<? extends Tuple> right = rightTable.collect(Collectors.toList());
        return TableStream.load(
                leftTable.flatMap(currentRow->right.stream().map(otherRow->Tuples.combine(currentRow, otherRow)))
          );
    }
    static TableStream<? extends Tuple> crossJoin(TupleList<Tuple> leftList,TupleList<Tuple> rightList){
        return TableStream.load(
                leftList.tableStream().flatMap(currentRow->rightList.tableStream().map(otherRow->Tuples.combine(currentRow, otherRow)))
        );
    }

    static TableStream<? extends Tuple> innerJoin(TableStream<? extends Tuple> leftTable,TableStream<? extends Tuple> rightTable, Predicate<Tuple> predicate){
        List<? extends Tuple> right = rightTable.collect(Collectors.toList());
        return TableStream.load(
                leftTable.flatMap(currentRow->right.stream().map(otherRow->Tuples.combine(currentRow, otherRow))).filter(predicate)
        );

    }
    static TableStream<? extends Tuple> innerJoin(TupleList<Tuple> leftList,TupleList<Tuple> rightList, Predicate<Tuple> predicate){
        return TableStream.load(
                leftList.tableStream().flatMap(currentRow->rightList.tableStream().map(otherRow->Tuples.combine(currentRow, otherRow))).filter(predicate)
        );

    }
}
