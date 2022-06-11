package com.zxw.jenkins;

import lombok.Data;

import java.util.Map;

/**
 * @author zxw
 * @date 2022/3/14 0:02
 */
@Data
public class BuildJob {
    private String folderName;
    private Integer port;
    private String jobName;
    private String serverName;
    private String env;
    private String[] publishServer;
    private Map<String, String> param;
}
