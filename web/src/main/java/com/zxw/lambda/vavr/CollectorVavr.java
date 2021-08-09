package com.zxw.lambda.vavr;

import io.vavr.Tuple2;
import io.vavr.collection.Array;
import io.vavr.collection.List;
import io.vavr.collection.Queue;

/**
 * @author zxw
 * @date 2021-06-12 14:08
 */
public class CollectorVavr {
    public static void main(String[] args) {
        List<Integer> list1 = List.of(1, 2, 3);
        Array array = Array.of("1", "2", 3);
        List<Integer> list2 = list1.tail().prepend(0);
        Queue<Integer> enqueue = Queue.of(1, 2, 3).enqueue(4).enqueue(5);
        Queue<Integer> queue = Queue.of(1, 2, 3);
        Tuple2<Integer, Queue<Integer>> dequeued = queue.dequeue();
        array.forEach(System.out::println);
    }
}
