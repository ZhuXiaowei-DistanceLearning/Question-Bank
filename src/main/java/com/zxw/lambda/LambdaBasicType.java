package com.zxw.lambda;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author zxw
 * @date 2021/5/26 16:49
 */
public class LambdaBasicType {
    /**
     * 使用蒙特卡洛模拟法并行化模拟掷骰子事件
     *
     * @return
     */
    public Map<Integer, Double> parallelDiceRolls() {
        double fraction = 1.0 / 10;
        return IntStream.range(0, 10)
                .parallel()
                .mapToObj(e -> e)
//                .mapToObj(Collectors.twoDiceThrows())
                .collect(Collectors.groupingBy(side -> side,
                        Collectors.summingDouble(n -> fraction)));
    }

    /**
     * 计算简单滑动平均数
     *
     * @param values
     * @param n
     * @return
     */
    public static double[] simpleMovingAverage(double[] values, int n) {
        double[] sums = Arrays.copyOf(values, values.length);
        Arrays.parallelPrefix(sums, Double::sum);
        int start = n - 1;
        return IntStream.range(start, sums.length)
                .mapToDouble(i -> {
                    double prefix = i == start ? 0 : sums[i - n];
                    return (sums[i] - prefix) / n;
                })
                .toArray();
    }
}
