package com.zxw.thread.batch;

/**
 * @author zxw
 * @date 2021-01-03 18:57
 */
public class TrafficShaper {
    private static final long MAX_DELAY = 30 * 1000;

    private final long congestionRetryDelayMs;
    private final long networkFailureRetryMs;

    private volatile long lastCongestionError;
    private volatile long lastNetworkFailure;

    TrafficShaper(long congestionRetryDelayMs, long networkFailureRetryMs) {
        this.congestionRetryDelayMs = Math.min(MAX_DELAY, congestionRetryDelayMs);
        this.networkFailureRetryMs = Math.min(MAX_DELAY, networkFailureRetryMs);
    }

    void registerFailure(TaskProcessor.ProcessingResult processingResult) {
        if (processingResult == TaskProcessor.ProcessingResult.Congestion) {
            lastCongestionError = System.currentTimeMillis();
        } else if (processingResult == TaskProcessor.ProcessingResult.TransientError) {
            lastNetworkFailure = System.currentTimeMillis();
        }
    }

    long transmissionDelay() {
        if (lastCongestionError == -1 && lastNetworkFailure == -1) {
            return 0;
        }

        long now = System.currentTimeMillis();
        if (lastCongestionError != -1) {
            long congestionDelay = now - lastCongestionError;
            if (congestionDelay >= 0 && congestionDelay < congestionRetryDelayMs) {
                return congestionRetryDelayMs - congestionDelay;
            }
            lastCongestionError = -1;
        }

        if (lastNetworkFailure != -1) {
            long failureDelay = now - lastNetworkFailure;
            if (failureDelay >= 0 && failureDelay < networkFailureRetryMs) {
                return networkFailureRetryMs - failureDelay;
            }
            lastNetworkFailure = -1;
        }
        return 0;
    }
}
