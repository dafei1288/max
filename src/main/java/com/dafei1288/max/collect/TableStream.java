package com.dafei1288.max.collect;

import com.dafei1288.max.collect.tuple.Tuple;

import java.io.Writer;
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
    Spliterator<T> spliterator();

    TupleList<? extends Tuple> toTupleList();

    TupleList<? extends Tuple> head(int i);
    TupleList<? extends Tuple> tail(int i);

    T getRow(int i);

    <E> List<E> getColum(int i);

    TableStream<? extends Tuple> crossJoin(TableStream<? extends Tuple> otherTable);

    TableStream<? extends Tuple> crossJoin(TupleList<? extends Tuple> otherTableList);

    TableStream<? extends Tuple> innerJoin(TableStream<? extends Tuple> otherTable, Predicate<Tuple> predicate);

    TableStream<? extends Tuple> innerJoin(TupleList<? extends Tuple> otherTableList, Predicate<Tuple> predicate);

    TableStream<? extends Tuple> leftOuterJoin(TableStream<? extends Tuple> otherTable, int leftKeyIndex, int rightKeyIndex);

    TableStream<? extends Tuple> leftOuterJoin(TupleList<? extends Tuple> otherTableList, int leftKeyIndex, int rightKeyIndex);

    TableStream<? extends Tuple> rightOuterJoin(TableStream<? extends Tuple> otherTable, int leftKeyIndex, int rightKeyIndex);

    TableStream<? extends Tuple> rightOuterJoin(TupleList<? extends Tuple> otherTableList, int leftKeyIndex, int rightKeyIndex);

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

    Map<? extends Tuple, List<T>> groupBy(Integer... groupIndex);

    Map<Tuple, Double> aggregateBy(Integer aggColIndex, Integer... groupIndex);

    Map<Tuple, Tuple> aggregateMultiColumsBy(List<Integer> aggIndexs, List<Integer> groupIndexs);

    Map<Tuple, Tuple> aggregateMultiColumsAndHavingBy(List<Integer> aggIndexs, List<Integer> groupIndexs, Predicate<Map.Entry<? extends Tuple, ? extends Tuple>> predicate);

    void discrib();

    TableStream<T> subTable(long from, long to);

    TableStream<? extends Tuple> trans(Map<Integer, Function> transTable);

    TableStream<? extends Tuple> transFilter(Map<Integer, Function> transTable);

    TableStream<T> beginTrace();
    TableStream<T> trace();
    TableStream<T> trace(Writer writer);

    static <T> TableStream<T> empty() {
        return load(Stream.empty());
    }

    static <T> TableStream<T> generate(Supplier<? extends T> s) {
        return load(Stream.generate(s));
    }

    static TableStream<Void> generate() {
        return TableStream.generate(() -> null);
    }

    static <T> TableStream<T> generate(T value) {
        return TableStream.generate(() -> value);
    }


    static <T> TableStream<T> load(Stream<? extends T> stream) {
        if (stream == null) return TableStream.empty();
        if (stream instanceof TableStream) {
            return (TableStream<T>) stream;
        }
        return new TupleTableStream<T>(stream);
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

    static <T> TableStream<T> of(T t) {
        return load(Stream.of(t));
    }

    static <T> TableStream<T> of(T... values) {
        return load(Stream.of(values));
    }




    static TableStream<? extends Tuple> crossJoin(TableStream<? extends Tuple> leftTable, TableStream<? extends Tuple> rightTable) {
        List<? extends Tuple> right = rightTable.collect(Collectors.toList());
        return TableStream.load(
                leftTable.flatMap(currentRow -> right.stream().map(otherRow -> Tuples.combine(currentRow, otherRow)))
        );
    }

    static TableStream<? extends Tuple> crossJoin(TupleList<Tuple> leftList, TupleList<Tuple> rightList) {
        return TableStream.load(
                leftList.tableStream().flatMap(currentRow -> rightList.tableStream().map(otherRow -> Tuples.combine(currentRow, otherRow)))
        );
    }

    static TableStream<? extends Tuple> innerJoin(TableStream<? extends Tuple> leftTable, TableStream<? extends Tuple> rightTable, Predicate<Tuple> predicate) {
        List<? extends Tuple> right = rightTable.collect(Collectors.toList());
        return TableStream.load(
                leftTable.flatMap(currentRow -> right.stream().map(otherRow -> Tuples.combine(currentRow, otherRow))).filter(predicate)
        );

    }

    static TableStream<? extends Tuple> innerJoin(TupleList<Tuple> leftList, TupleList<Tuple> rightList, Predicate<Tuple> predicate) {
        return TableStream.load(
                leftList.tableStream().flatMap(currentRow -> rightList.tableStream().map(otherRow -> Tuples.combine(currentRow, otherRow))).filter(predicate)
        );
    }
    static TableStream<? extends Tuple> leftOuterJoin(TableStream<? extends Tuple> leftTable, TableStream<? extends Tuple> rightTable, int leftKeyIndex, int rightKeyIndex){
        List<? extends Tuple> right = rightTable.collect(Collectors.toList());
        int esize = right.get(0).size();
        return TableStream.load(
                leftTable.flatMap(currentRow->
                        defaultIfEmpty(
                                right.stream().filter(otherRow->Objects.equals(currentRow.get(leftKeyIndex),otherRow.get(rightKeyIndex))),()->{return Tuples.createEmptyTuple(esize);}
                        ).map(otherRow->Tuples.combine(currentRow,otherRow)))
        );
    }
    static TableStream<? extends Tuple> leftOuterJoin(TupleList<Tuple> leftList, TupleList<Tuple> rightList, int leftKeyIndex, int rightKeyIndex){
        int esize = rightList.tableStream().findFirst().get().size();
        return TableStream.load(
                leftList.tableStream().flatMap(currentRow->
                        defaultIfEmpty(
                                rightList.tableStream().filter(otherRow->Objects.equals(currentRow.get(leftKeyIndex),otherRow.get(rightKeyIndex))),()->{return Tuples.createEmptyTuple(esize);}
                        ).map(otherRow->Tuples.combine(currentRow,otherRow)))
        );
    }
    static TableStream<? extends Tuple> rightOuterJoin(TableStream<? extends Tuple> leftTable, TableStream<? extends Tuple> rightTable, int leftKeyIndex, int rightKeyIndex) {
        return leftOuterJoin(rightTable,leftTable,leftKeyIndex,rightKeyIndex);
    }
    static TableStream<? extends Tuple> rightOuterJoin(TupleList<Tuple> leftList, TupleList<Tuple> rightList, int leftKeyIndex, int rightKeyIndex) {
        return  leftOuterJoin(rightList,leftList,leftKeyIndex,rightKeyIndex);
    }


    static TableStream<? extends Tuple> union(TupleList<Tuple> leftList, TupleList<Tuple> rightList){
        return TableStream.load(Stream.concat(leftList.tableStream(),rightList.tableStream()));
    }



    static TableStream<? extends Tuple> trans(TableStream<? extends Tuple> tableStream,Map<Integer, Function> transTable){
        return TableStream.load(tableStream.map(it->{
            return it.trans(transTable);
        }));
    }


    static TableStream<? extends Tuple> transFilter(TableStream<? extends Tuple> tableStream,Map<Integer, Function> transTable){
        return TableStream.load(tableStream.map(it->{
            return it.transFilter(transTable);
        }));
    }

    static  Stream<? extends Tuple> throwIfEmpty(Stream<? extends Tuple> stream) {
        Iterator iterator = stream.iterator();
        if (iterator.hasNext()) {
            return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 0), false);
        } else {
            throw new NoSuchElementException("empty stream");
        }
    }

    static Stream<? extends Tuple> defaultIfEmpty(Stream<? extends Tuple> stream, com.google.common.base.Supplier<? extends Tuple> supplier) {
        Iterator iterator = stream.iterator();
        if (iterator.hasNext()) {
            return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 0), false);
        } else {
            return Stream.of(supplier.get());
        }
    }


}
