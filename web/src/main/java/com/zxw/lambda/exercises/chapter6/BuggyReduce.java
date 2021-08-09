package com.zxw.lambda.exercises.chapter6;

import java.util.List;

public class BuggyReduce {

    public static int multiplyThrough(List<Integer> linkedListOfNumbers) {
        return linkedListOfNumbers.stream()
                                  .reduce(5, (acc, x) -> x * acc);
    }

}
