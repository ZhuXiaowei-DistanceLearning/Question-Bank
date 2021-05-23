package com.zxw.lambda;

import java.util.List;

/**
 * @author zxw
 * @date 2021-05-23 13:26
 */
public class LambdaParallel {
    // 串行化计算专辑曲目长度
    public int serialArraySum(List<Album> albums) {
        return albums.stream().flatMap(Album::getTracks).mapToInt(Track::getLength).sum();
    }

    // 并行化计算
    public int parallelArraySum(List<Album> albums) {
        return albums.parallelStream().flatMap(Album::getTracks).mapToInt(Track::getLength).sum();
    }

    public static void main(String[] args) {

    }
}
