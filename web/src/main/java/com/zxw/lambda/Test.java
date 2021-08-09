package com.zxw.lambda;

import com.zxw.lambda.examples.chapter5.StringCollector;
import com.zxw.lambda.examples.chapter5.StringCombiner;

import javax.swing.text.DateFormatter;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

/**
 * 让代码在多核CPU上高校运行，批量处理数据的并行类库
 * Lambda其实返回的是一个函数，而不是其中的代码结果
 * // * 为了编写 这类处理批量数据的并行类库，需要在语言层面上修改现有的 Java：增加 Lambda 表达式。
 *
 * @author zxw
 * @date 2021-05-21 21:17
 */
public class Test {

    // 不包含参数，返回值类型为void，该lambda类型实现了Runnable接口
    Runnable noArguments = () -> System.out.println("Hello World");
    ActionListener oneArgument = event -> System.out.println("button clicked");
    // 代码块，可以用返回或抛出异常来退出
    Runnable multiStatement = () -> {
        System.out.print("Hello");
        System.out.println(" World");
    };
    // 多个参数的方法，这行代码不是将两个数字相加，而是创建了一个函数，用来计算两个数字相加的结果，变量 add 的类型是 BinaryOperator<Long>，它不是两个数字的和， 而是将两个数字相加的那行代码。
    BinaryOperator<Long> add = (x, y) -> x + y;
    BinaryOperator<Long> addExplicit = (Long x, Long y) -> x + y;

    public static void main(String[] args) {
        ThreadLocal<DateFormatter> formatter = ThreadLocal.withInitial(() -> new DateFormatter(new SimpleDateFormat("dd-MMM-yyyy")));
        List<String> strings = Arrays.asList("1", "2", "3");
        Stream<String> stringStream = strings.stream().filter(e -> {
            System.out.println(e);
            return e != null;
        });
        System.out.println("end");
    }

    // reduce
    int count = Stream.of(1, 2, 3).reduce(0, (acc, element) -> acc + element);
    BinaryOperator<Integer> accumulator = (acc, element) -> acc + element;
    int count1 = accumulator.apply(accumulator.apply(accumulator.apply(0, 1), 2), 3);

    public void test(List<Artist> artists) {
        StringCombiner combined = artists.stream().map(Artist::getName).reduce(new StringCombiner(", ", "[", "]"), StringCombiner::add, StringCombiner::merge);
        String result = combined.toString();
        String result2 = artists.stream().map(Artist::getName).collect(new StringCollector(", ", "[", "]"));
    }

}
