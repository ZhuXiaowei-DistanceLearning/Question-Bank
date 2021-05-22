package com.zxw.lambda;

import lombok.Data;

import java.util.List;

/**
 * @author zxw
 * @date 2021-05-21 23:36
 */
@Data
public class Album {
    private String name;
    private List<Track> trackList;
}
