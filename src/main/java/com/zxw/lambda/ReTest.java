package com.zxw.lambda;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
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

    public static void main(String[] args) {

    }
}
