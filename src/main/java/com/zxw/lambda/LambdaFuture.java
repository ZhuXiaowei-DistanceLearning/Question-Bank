package com.zxw.lambda;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zxw
 * @date 2021/5/28 14:39
 */
public class LambdaFuture {
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    CompletableFuture<Artist> createFuture(String id) {
        CompletableFuture<Artist> future = new CompletableFuture<>();
//        startJob(future);
        return future;
    }

    CompletableFuture<Track> lookupTrack(String id) {
        return CompletableFuture.supplyAsync(() -> {
            Track track = new Track();
            return track;
        }, executorService);
    }

    public static void main(String[] args) {

    }
}
