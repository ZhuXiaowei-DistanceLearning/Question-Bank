package com.zxw.jenkins;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import com.offbytwo.jenkins.model.FolderJob;
import com.offbytwo.jenkins.model.Job;
import com.zxw.gitlab.GitlabOauth;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 非连接池的API：一般命名为XXXConnection，以区分其是基于连接池还是单连接的，而
 * 不建议命名为XXXClient或直接是XXX。直接连接方式的API基于单一连接，每次使用都需
 * 要创建和断开连接，性能一般，且通常不是线程安全的。对应到连接池的结构示意图中，
 * 这种形式相当于没有右边连接池那个框，客户端直接连接服务端创建连接。
 *
 * @author zxw
 * @date 2022/3/12 12:28
 */
@Slf4j
public class JenkinsConnection {
    // 连接 Jenkins 需要设置的信息
    static final String JENKINS_URL = "http://jenkins.local.ap-ec.cn";
    String cookie = "jenkins-timestamper-offset=-28800000; jenkins-timestamper=system; jenkins-timestamper-local=true; screenResolution=1920x1080; ";

    @SneakyThrows
    public void login() {
        GitlabOauth gitlabOauth = new GitlabOauth();
        Map<String, String> headers = gitlabOauth.oauth();
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(JENKINS_URL)
                .get()//默认就是GET请求，可以不写
                .headers(Headers.of(headers))
                .build();
        try (Response response = client.newCall(request).execute()) {
            log.info("{}", response.body().string());
        }
    }


    public static JenkinsHttpClient getClient() {
        JenkinsHttpClient jenkinsHttpClient = null;
        try {
            jenkinsHttpClient = new JenkinsHttpClient(new URI(JENKINS_URL), "develop", "114d0c4ea1ac2f73a4a7d6665e531bb508");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return jenkinsHttpClient;
    }

    /**
     * 连接 Jenkins
     */
    public static JenkinsServer connection(JenkinsHttpClient client) {
        JenkinsServer jenkinsServer = new JenkinsServer(client);
        return jenkinsServer;
    }

    public void buildJob(BuildJob buildJob) throws IOException {
        JenkinsHttpClient client = JenkinsConnection.getClient();
        JenkinsServer server = JenkinsConnection.connection(client);
        if (buildJob.getPublishServer() != null) {
            buildJob.getParam().put("server_list", Stream.of(buildJob.getPublishServer()).collect(Collectors.joining("")));
        }
        server.getJob(new FolderJob("/", buildJob.getFolderName()), buildJob.getJobName()).build(buildJob.getParam());
    }

    public static void main(String[] args) throws Exception {
        JenkinsHttpClient client = JenkinsConnection.getClient();
        JenkinsServer server = JenkinsConnection.connection(client);
        FolderJob folderJob = new FolderJob("/", "/job/平台团队/job/JENKINS测试/");
        Map<String, Job> jobs = server.getJobs(folderJob);
        Map<String, String> param = new HashMap<>();
        param.put("name", "producer-test");
        param.put("git_repository", "http://git.ap-ec.cn/develop/micro-server-test.git");
        param.put("server_list", "producer-test");
        param.put("branch", "master");
        param.put("deploy_action", "发布");
        server.getJob(folderJob, "producer-test").build(param);
        log.info("{}", "test");
    }

}
