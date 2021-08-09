package com.zxw.lambda;

import lombok.Data;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author zxw
 * @date 2021-05-21 23:36
 */
@Data
public class Album {
    private String name;
    private List<Track> trackList;
    private Stream<Track> tracks;

    /**
     * 找出一组专辑上曲目的平均数
     *
     * @param albums
     * @return
     */
    public double averageNumberOfTracks(List<Album> albums) {
        return albums.stream()
                .collect(Collectors.averagingInt(album -> album.getTrackList().size()));
    }

    /**
     * 使用summaryStatistics 方法统计曲目长度
     *
     * @param album
     */
    public static void printTrackLengthStatistics(Album album) {
        IntSummaryStatistics trackLengthStats
                = album.getTracks()
                .mapToInt(track -> track.getLength())
                .summaryStatistics();
        System.out.printf("Max: %d, Min: %d, Ave: %f, Sum: %d",
                trackLengthStats.getMax(),
                trackLengthStats.getMin(),
                trackLengthStats.getAverage(),
                trackLengthStats.getSum());
    }
}
