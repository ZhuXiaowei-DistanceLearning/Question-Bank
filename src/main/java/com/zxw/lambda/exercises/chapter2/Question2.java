package com.zxw.lambda.exercises.chapter2;

import com.zxw.lambda.exercises.Exercises;

import javax.swing.text.DateFormatter;

import static java.lang.ThreadLocal.withInitial;

public class Question2 {

    public static ThreadLocal<DateFormatter> formatter
            = Exercises.replaceThisWithSolution();

}
