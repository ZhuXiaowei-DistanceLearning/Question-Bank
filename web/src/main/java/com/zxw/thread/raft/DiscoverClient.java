package com.zxw.thread.raft;

import java.util.List;

/**
 * @author zxw
 * @date 2021-01-07 16:38
 */
public class DiscoverClient extends Thread{
    private String id;
    private DiscoverClient leader;
    private List<DiscoverClient> peers;
    private State state;
    private Integer term;
    private String voteFor;
    private int commitIndex;
    private ElectionAlarm electionAlarm;
    // 选举超时，15-30秒之间
    private volatile long leaderDueMs = 0;
    // 心跳超时
    private volatile long heartbeatDueMs = 0;

    @Override
    public void run() {
        super.run();
    }


}
