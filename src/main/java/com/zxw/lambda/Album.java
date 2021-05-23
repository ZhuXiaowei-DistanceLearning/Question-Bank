package com.zxw.lambda;

import lombok.Data;

import java.util.List;
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
}
