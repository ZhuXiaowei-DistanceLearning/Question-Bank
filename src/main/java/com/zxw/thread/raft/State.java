package com.zxw.thread.raft;

/**
 * @author zxw
 * @date 2021-01-07 17:05
 */
public enum State {
    /**
     * Leader of the cluster, only one leader stands in a cluster.
     */
    LEADER,
    /**
     * Follower of the cluster, report to and copy from leader.
     */
    FOLLOWER,
    /**
     * Candidate leader to be elected.
     */
    CANDIDATE
}
