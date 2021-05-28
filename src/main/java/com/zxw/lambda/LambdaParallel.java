package com.zxw.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author zxw
 * @date 2021-05-23 13:26
 */
public class LambdaParallel {
    // 串行化计算专辑曲目长度
    public static int serialArraySum(List<Album> albums) {
        return albums.stream().flatMap(Album::getTracks).peek(e -> System.out.println(e.getName() + "-" + e.getLength())).mapToInt(Track::getLength).sum();
    }

    // 并行化计算
    public static int parallelArraySum(List<Album> albums) {
        return albums.parallelStream().flatMap(Album::getTracks).mapToInt(Track::getLength).sum();
    }

    /**
     * parallelSetAll:使用Lambda 表达式更新数组元素
     * parallelSort:并行化对数组元素排序
     */
    public static double[] parallelInitialize(int size) {
        double[] values = new double[size];
        Arrays.parallelSetAll(values, i -> i);
        return values;
    }

    /**
     * parallelPrefix:任意给定一个函数，计算数组的和
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

    public static void main(String[] args) {
        List<Album> album = getAlbum();
        List<Album> album2 = getAlbum();
        System.out.println(serialArraySum(album));
        System.out.println(parallelArraySum(album2));
    }

    private static List<Album> getAlbum() {
        List<Album> list = new ArrayList<>();
        Album album = new Album();
        album.setName("你好");
        album.setTrackList(getTrackList());
        album.setTracks(getTrackList().stream());
        Album album2 = new Album();
        album2.setName("你好2");
        album2.setTrackList(getTrackList());
        album2.setTracks(getTrackList().stream());
        list.add(album);
        list.add(album2);
        return list;
    }

    private static List<Track> getTrackList() {
        List<Track> trackList = new ArrayList<>();
        Track track = new Track();
        track.setName("1");
        track.setLength(10);
        Track track2 = new Track();
        track2.setName("2");
        track2.setLength(20);
        trackList.add(track);
        trackList.add(track2);
        return trackList;
    }
}
