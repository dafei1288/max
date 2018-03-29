package com.dafei1288.max.data;

import com.dafei1288.max.collect.TableStream;
import com.dafei1288.max.collect.Tuples;
import com.dafei1288.max.collect.tuple.Tuple;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.io.File;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CsvLoader implements DataLoader{

    public final static String COMMA = ",";
    public final static String STOP = ".";
    public final static String SEMICOLON = ";";
    public final static int HEADERLINE_NUMBER = 1;

    public static <R> List<R> loadData(File file,Function<String, R> mapper,Collector<R, ?, List<R>> collector) throws IOException {
        return Files.readAllLines(file.toPath()).stream().map(mapper).collect(collector);
    }
    public static List<Tuple> loadData(File file,final String spilter) throws IOException {
        return loadData(file,spilter,false);
    }

    public static List<Tuple> loadData(String filename,final String spilter) throws IOException {
        return loadData(new File(filename),spilter,false);
    }
    public static List<Tuple> loadData(File file,final String spilter,final boolean includeHead) throws IOException {
        List<Tuple> temp = loadData(file,it->{
            return Tuples.tuple(it.split(spilter));
        },Collectors.toList());
        if(includeHead){
            return temp.subList(HEADERLINE_NUMBER,temp.size());
        }
        return temp;
    }

    public static List<Tuple> loadData(String filename,final String spilter,final boolean includeHead) throws IOException {
        return loadData(new File(filename),spilter,includeHead);
    }
    public static TableStream<? extends Tuple> loadDataToTableStream(String filename,final String spilter,final boolean includeHead) throws IOException{
        return loadDataToTableStream(new File(filename),spilter,includeHead);
    }
    public static TableStream<? extends Tuple> loadDataToTableStream(File file,final String spilter,final boolean includeHead) throws IOException{
        List<Tuple> temp = loadData(file,spilter,includeHead);
        return TableStream.load(temp);
    }
}