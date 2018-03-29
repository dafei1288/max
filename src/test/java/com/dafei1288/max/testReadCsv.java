package com.dafei1288.max;

import com.dafei1288.max.collect.TableStream;
import com.dafei1288.max.collect.TupleList;
import com.dafei1288.max.collect.TupleTable;
import com.dafei1288.max.collect.Tuples;
import com.dafei1288.max.collect.tuple.Tuple;
import com.dafei1288.max.data.CsvLoader;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class testReadCsv {
    public static TableStream<Tuple> tts;
    public static Path p = Paths.get("src/test/resources/train.csv");
    public static TupleList tl;
//    public static File file = p.toFile();toFile
    @BeforeClass
    public static void readData() throws IOException {
        tl = new TupleList();

        Files.readAllLines(p).stream().forEach(it->{
            tl.add(Tuples.tuple(it.split(",")));
        });

    }

    @Test
    public void testFilter(){

//        tts = tts.subTable(1,Integer.parseInt(tts.count()+""));
        tl = tl.subTupleList(1,tl.size());
//        tts = tl.subTupleList(1,tl.size()).tableStream();
        long alived = tl.tableStream().filter(it->((Tuple)it).getInteger(1)==1).count();
        long deaded = tl.tableStream().filter(it->((Tuple)it).getInteger(1)==0).count();
        System.out.println("alive = "+alived+" , deded = "+deaded+" , all = "+(alived+deaded));
    }

    @Test
    public void testJoin(){
        TupleList rightList = new TupleList();
        rightList.add(Tuples.tuple("0","死"));
        rightList.add(Tuples.tuple("1","活"));


        TupleList mt = tl.subTupleList(1,tl.size());
        System.out.println(mt.size());


//        TableStream.innerJoin(mt.tableStream(),rightList.tableStream(),it->{
//            return Objects.equals(((Tuple)it).get(1),((Tuple)it).get(13));
//        }).forEach(System.out::println);
//        TableStream.crossJoin(mt,rightList).filter(it->Objects.equals(((Tuple)it).get(1),((Tuple)it).get(13))).filter(it->((Tuple)it).getString(14).equals("活")).forEach(System.out::println);
        TableStream.crossJoin(mt.tableStream(),rightList.tableStream()).forEach(System.out::println);
        TableStream.innerJoin(tl,rightList,it->{return Objects.equals(it.get(1), it.get(13));}).filter(it->((Tuple)it).getString(14).equals("活")).map(it->((Tuple)it).getString(3)+((Tuple)it).getString(4)).forEach(System.out::println);
        mt.tableStream().innerJoin(rightList.tableStream(),it->{return Objects.equals(((Tuple)it).get(1),((Tuple)it).get(13));}).where(it->((Tuple)it).getString(14).equals("活")).select(it->((Tuple)it).getString(3)+((Tuple)it).getString(4)).forEach(System.out::println);
        //        tl.tableStream().innerJoin(leftTable,it->{
//            //System.out.println("1: "+it.get(1)+" 13: "+it.get(13));
//            return Objects.equals(((Tuple)it).get(1),((Tuple)it).get(13));}).forEach(System.out::println);
                //.filter(it->Objects.equals(((Tuple)it).get(1),"0"))


//        tl.tableStream().innerJoin(leftTable,it->{
//            //System.out.println("1: "+it.get(1)+" 13: "+it.get(13));
//            return Objects.equals(((Tuple)it).get(1),((Tuple)it).get(13));}).select(it->{return Tuples.tuple(((Tuple)it).get(0),((Tuple)it).get(1),((Tuple)it).get(2),((Tuple)it).get(14));}).forEach(System.out::println);
    }

    @Test
    public void testCsvLoader(){
        try {
            TupleList<Tuple> rightList = new TupleList();
            rightList.add(Tuples.tuple("0","死"));
            rightList.add(Tuples.tuple("1","活"));

            CsvLoader.loadDataToTableStream("src/test/resources/train.csv",",",true)
                    .crossJoin(rightList)
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
