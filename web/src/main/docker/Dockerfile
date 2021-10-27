FROM java:8-alpine
LABEL maintainer="author zxw"

RUN echo -e "https://mirror.tuna.tsinghua.edu.cn/alpine/v3.4/main\n\
https://mirror.tuna.tsinghua.edu.cn/alpine/v3.4/community" > /etc/apk/repositories

RUN apk --update add curl bash ttf-dejavu && \
      rm -rf /var/cache/apk/*
RUN apk add -U tzdata && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo "Asia/Shanghai" > /etc/timezone \
    && apk del tzdata

# 安装字体，解决部分服务依赖
RUN mkdir -p /usr/share/fonts/simsun && mkdir -p /usr/share/fonts/simfang && curl https://xxx.aliyuncs.com/fonts/simsun.ttf -o /usr/share/fonts/simsun/simsun.ttf && curl https://xxx.aliyuncs.com/fonts/simfang.ttf -o /usr/share/fonts/simfang/simfang.ttf

# 安装监控埋点
RUN curl https://xxxx/skywalking/agent.tar.gz -o /home/agent.tar.gz && cd /home && tar -xzf agent.tar.gz && rm -rf /home/agent.tar.gz
# 添加Spring 相关的可选插件
RUN cp /home/agent/optional-plugins/apm-spring-* /home/agent/plugins/
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh
# 1.0.0 初版容器
# 1.0.1 增加字体服务依赖
# 1.0.2 增加skywalking埋点
# 1.0.3 增加spring可选组件
# 1.0.4 springboot优雅停机
ENTRYPOINT "/entrypoint.sh"
