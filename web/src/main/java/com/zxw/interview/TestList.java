package com.zxw.interview;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.IntStream;

/**
 * @author zxw
 * @date 2022/8/17 9:18
 */
@Slf4j
public class TestList {

    public static void main(String[] args) {
        Random random = new Random(System.currentTimeMillis());
        ArrayList<Integer> l1 = new ArrayList<>();
        LinkedList<Integer> l2 = new LinkedList<>();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("ArrayList");
        rangeOperate(e -> l1.add(random.nextInt(l1.size()+1), e));
        l1.clear();
        stopWatch.stop();
        System.gc();
        stopWatch.start("LinkedList");
        rangeOperate(e -> l2.add(random.nextInt(l2.size()+1), e));
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
    }

    public static void rangeOperate(Consumer<Integer> consumer) {
        IntStream.range(0, 100000)
                .forEach(e -> consumer.accept(e));
    }
}
