package com.zxw.lambda.exercises;

public class ExerciseNotCompletedException extends RuntimeException {

    public ExerciseNotCompletedException() {
        super("Please remove this line of code and implement the exercise");
    }

}
