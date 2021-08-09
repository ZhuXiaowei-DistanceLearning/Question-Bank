package com.zxw.lambda;

import lombok.Data;

import java.util.stream.Stream;

/**
 * @author zxw
 * @date 2021-05-21 23:36
 */
@Data
public class Artist {
    private String name;
    private String nationality;
    private Stream<Integer> members;
    private boolean sSolo;
    private Integer length;

    public boolean isSolo() {
        return sSolo;
    }

}
