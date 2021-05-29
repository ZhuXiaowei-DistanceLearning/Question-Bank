package com.zxw.lambda.exercises.chapter4;

import com.zxw.lambda.examples.chapter1.Artist;
import com.zxw.lambda.exercises.Exercises;

import java.util.stream.Stream;

/** A Performance by some musicians - eg an Album or Gig. */
public interface PerformanceFixed {

    public String getName();

    public Stream<Artist> getMusicians();

    public default Stream<Artist> getAllMusicians() {
        return Exercises.replaceThisWithSolution();
    }

}
