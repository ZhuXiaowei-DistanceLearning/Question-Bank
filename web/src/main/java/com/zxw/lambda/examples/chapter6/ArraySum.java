package com.zxw.lambda.examples.chapter6;

import com.zxw.lambda.examples.chapter1.Album;
import com.zxw.lambda.examples.chapter1.SampleData;
import com.zxw.lambda.examples.chapter1.Track;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

//@State(Scope.Benchmark)
//@BenchmarkMode(Mode.AverageTime)
public class ArraySum {

    public static void main(String[] ignore) throws IOException {
        final String[] args = {
            ".*ArraySum.*",
            "-wi",
            "5",
            "-i",
            "5"
        };
//        Main.main(args);
    }

    public List<Album> albums;

//    @Setup
    public void initAlbums() {
        int n = Integer.getInteger("arraysum.size", 1000);
        albums = IntStream.range(0, n)
                          .mapToObj(i -> SampleData.aLoveSupreme.copy())
                          .collect(toList());
    }

//    @GenerateMicroBenchmark
    // BEGIN serial
public int serialArraySum() {
    return albums.stream()
                 .flatMap(Album::getTracks)
                 .mapToInt(Track::getLength)
                 .sum();
}
    // END serial

//    @GenerateMicroBenchmark
    // BEGIN parallel
public int parallelArraySum() {
    return albums.parallelStream()
                 .flatMap(Album::getTracks)
                 .mapToInt(Track::getLength)
                 .sum();
}
    // END parallel
    
}
