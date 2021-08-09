package com.zxw.lambda.answers.chapter3;


import com.zxw.lambda.examples.chapter1.Album;
import com.zxw.lambda.examples.chapter1.Artist;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Question1 {
    public static int addUp(Stream<Integer> numbers) {
        return numbers.reduce(0, (acc, x) -> acc + x);
    }

    public static List<String> getNamesAndOrigins(List<Artist> artists) {
        return artists.stream()
                      .flatMap(artist -> Stream.of(artist.getName(), artist.getNationality()))
                      .collect(toList());
    }

    public static List<Album> getAlbumsWithAtMostThreeTracks(List<Album> input) {
        return input.stream()
                    .filter(album -> album.getTrackList().size() <= 3)
                    .collect(toList());
    }
}
