package com.zxw.lambda;


import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * default:解决第三方库依赖的问题
 *
 * @author zxw
 * @date 2021-05-21 23:38
 */
public class ReTest {
    /**
     * 找出长度大于一分钟的曲目
     *
     * @param albums
     * @return
     */
    public Set<String> findLongTracks(List<Album> albums) {
        Set<String> trackNames = new HashSet<>();
        for (Album album : albums) {
            for (Track track : album.getTrackList()) {
                if (track.getLength() > 60) {
                    String name = track.getName();
                    trackNames.add(name);
                }
            }
        }
        return trackNames;
    }

    public Set<String> findLongTracks2(List<Album> albums) {
        Set<String> trackNames = new HashSet<>();
        albums.forEach(album -> {
            album.getTrackList().forEach(track -> {
                if (track.getLength() > 60) {
                    String name = track.getName();
                    trackNames.add(name);
                }
            });
        });
        return trackNames;
    }

    public Set<String> findLongTracks3(List<Album> albums) {
        Set<String> trackNames = new HashSet<>();
        albums.stream()
                .flatMap(album -> album.getTrackList().stream())
                .filter(track -> track.getLength() > 60)
                .map(track -> track.getName())
                .forEach(name -> trackNames.add(name));
        return trackNames;
    }

    public Set<String> findLongTracks4(List<Album> albums) {
        Set<String> trackNames = albums.stream()
                .flatMap(album -> album.getTrackList().stream())
                .filter(track -> track.getLength() > 60)
                .map(track -> track.getName())
                .collect(Collectors.toSet());
        return trackNames;
    }

    public static void printTrackLengthStatistics(Album album) {
        IntSummaryStatistics trackLengthStats = album.getTracks().mapToInt(track -> track.getLength()).summaryStatistics();
        System.out.printf("Max: %d, Min: %d, Ave: %f, Sum: %d", trackLengthStats.getMax(), trackLengthStats.getMin(), trackLengthStats.getAverage(), trackLengthStats.getSum());
    }

    // 将艺术家组成的流分成乐队和独唱歌手两部分
    public Map<Boolean, List<Artist>> bandsAndSolo(Stream<Artist> artists) {
        return artists.collect(Collectors.partitioningBy(artist -> artist.isSolo()));
    }

    // 成员最多的乐队
    public Optional<Artist> biggestGroup(Stream<Artist> artists) {
        Function<Artist, Long> getCount = artist -> artist.getMembers().count();
        return artists.collect(Collectors.maxBy(Comparator.comparing(getCount)));
    }

    // 平均数
    public double averageNumberOfTracks(List<Album> albums) {
        return albums.stream().collect(Collectors.averagingInt(album -> album.getTrackList().size()));
    }

    /**
     * 因为这些概念描述了数据上的操作，明确了要达成什么转化，而不是说明如何转化。这种方式写出的代码，潜在的缺陷更少，更 直    * 接地表达了程序员的意图。 明确要达成什么转化，而不是说明如何转化的另外一层含义在于写出的函数没有副作用。 这一点非常     * 重要，这样只通过函数的返回值就能充分理解函数的全部作用。
     *
     * @param args
     */
    public static void main(String[] args) {

    }
}
