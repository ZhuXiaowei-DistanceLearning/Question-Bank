package com.zxw.leetcode.topic;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author zxw
 * @date 2022/8/3 9:31
 */
public class Graph {

    public static void main(String[] args) {
        Graph graph = new Graph();
    }

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<Integer>[] lists = buildGraph(numCourses, prerequisites);
        boolean[] visited = new boolean[numCourses];
        boolean[] onPath = new boolean[numCourses];
        AtomicBoolean res = new AtomicBoolean(false);
        for (int i = 0; i < numCourses; i++) {
            dfs(lists, i, visited, onPath, res);
        }
        return !res.get();
    }

    public void dfs(List<Integer>[] lists, int s, boolean[] visited, boolean[] onPath, AtomicBoolean res) {
        if(onPath[s]){
            res.set(true);
        }
        if (visited[s] || res.get()) {
            return;
        }
        onPath[s] = true;
        visited[s] = true;
        for (int t : lists[s]) {
            dfs(lists, t, visited, onPath, res);
        }
        onPath[s] = false;
    }

    public List<Integer>[] buildGraph(int numCourses, int[][] prerequisites) {
        List<Integer>[] graph = new LinkedList[numCourses];
        for (int i = 0; i < numCourses; i++) {
            graph[i] = new LinkedList<>();
        }
        for (int[] edge : prerequisites) {
            int form = edge[1], to = edge[0];
            graph[form].add(to);
        }
        return graph;
    }
}
