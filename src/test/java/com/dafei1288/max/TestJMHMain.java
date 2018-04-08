package com.dafei1288.max;

import org.junit.Test;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.profile.StackProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class TestJMHMain {
    public static void main(String[] args)  throws RunnerException {

        Options opt = new OptionsBuilder()
                .include(".*" + testReadCsv.class.getSimpleName() + ".*")
                .mode(Mode.All)
                .warmupIterations(0)
                .measurementIterations(5)
                //.addProfiler(StackProfiler.class)
                .forks(1)
                .build();
        new Runner(opt).run();
    }


//    @Test
//    public void measureName() {
//                Options opt = new OptionsBuilder()
//                .include(".*" + testReadCsv.class.getSimpleName() + ".*")
//                .forks(1)
//                .build();
//        try {
//            new Runner(opt).run();
//        } catch (RunnerException e) {
//            e.printStackTrace();
//        }
//    }
}
