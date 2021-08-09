package com.zxw.lambda.vavr;

import io.vavr.Function2;
import io.vavr.Function3;
import io.vavr.Tuple;
import io.vavr.Tuple2;

/**
 * @author zxw
 * @date 2021-06-12 20:38
 */
public class TupleVavr {
    public static void main(String[] args) {
        // (Java, 8)
        Tuple2<String, Integer> java8 = Tuple.of("Java", 8);
// "Java"
        String s1 = java8._1;
// 8
        Integer i1 = java8._2;

        // (vavr, 1)
        Tuple2<String, Integer> that = java8.map(
                s -> s.substring(2) + "vr",
                i -> i / 8
        );

        // (vavr, 1)
        Tuple2<String, Integer> that2 = java8.map(
                (s, i) -> Tuple.of(s.substring(2) + "vr", i / 8)
        );

        // "vavr 1"
        String that3 = java8.apply(
                (s, i) -> s.substring(2) + "vr " + i / 8
        );
    }

    // sum.apply(1, 2) = 3
    Function2<Integer, Integer, Integer> sum = (a, b) -> a + b;
    Function2<Integer, Integer, Integer> sum2 = new Function2<Integer, Integer, Integer>() {
        @Override
        public Integer apply(Integer a, Integer b) {
            return a + b;
        }
    };

    Function3<String, String, String, String> function3 =
            Function3.of(this::methodWhichAccepts3Parameters);

    private <R, T1, T2, T3> R methodWhichAccepts3Parameters(T1 t1, T2 t2, T3 t3) {
        return null;
    }
}
