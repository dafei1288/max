package com.dafei1288.max;

import com.dafei1288.max.collect.TableStream;
import com.dafei1288.max.collect.TupleList;
import com.dafei1288.max.collect.Tuples;
import com.dafei1288.max.collect.tuple.Tuple;
import com.dafei1288.max.data.CsvLoader;
import com.dafei1288.max.lambda.function.Functions;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.Objects;
import java.util.function.Function;

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

    @Test
    public void testTrans(){
        Function<String,Integer> a0 = (x)->(Integer.parseInt(x)+1);
        Function<String,String> a3 = x->"aaaa";


        Hashtable<Integer,Function> tansTable = new Hashtable<>();
        tansTable.put(0,a0);
        tansTable.put(3,a3);

        try {

            CsvLoader.loadDataToTableStream("src/test/resources/train.csv",",",true).map(it->{
                    return it.trans(tansTable);
            }).forEach(System.out::println);

            CsvLoader.loadDataToTableStream("src/test/resources/train.csv",",",true).transFilter(tansTable).forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Test
    public void testLoadBigData(){
        try {
            long start = System.currentTimeMillis();
//            TableStream<Tuple> ts = (TableStream<Tuple>) CsvLoader.loadDataToTableStream("C:\\Users\\dafei\\Desktop\\rb.csv",",",false);
            TupleList<Tuple> tl = CsvLoader.loadDataToTupleList("C:\\Users\\dafei\\Desktop\\rb.csv",",",false);
            long cost = System.currentTimeMillis()-start;
            System.out.println("load data = "+cost);

            start = System.currentTimeMillis();
            long count = tl.tableStream().count();
            cost = System.currentTimeMillis()-start;
            System.out.println("count = "+count+" , cost = "+cost);



            start = System.currentTimeMillis();
            Double agg = tl.tableStream().map(it-> it.getDouble(6)).reduce((a, b)->{return b+=a;}).get();
            cost = System.currentTimeMillis()-start;
            System.out.println("agg = "+agg+" , cost = "+cost);


            tl.tableStream().beginTrace().innerJoin(tl,it->Objects.equals(it.get(0),it.get(7))).forEach(System.out::println);//.trace();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoadBigData1(){
        try {
            long start = System.currentTimeMillis();
            Double count = CsvLoader.loadDataToTableStream("C:\\Users\\dafei\\Desktop\\rb.csv",",",false).map(it-> it.getDouble(6)).reduce((a, b)->{return b+=a;}).get();
            long cost = System.currentTimeMillis()-start;
            System.out.print("sum = "+count+" , cost = "+cost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFunction(){
        try {
            CsvLoader.loadDataToTableStream("C:\\Users\\dafei\\Desktop\\rb.csv",",",false).map(it-> it.getDouble(6)).map(Functions.MathFunc.COS).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
