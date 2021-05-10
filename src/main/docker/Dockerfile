#这是基础镜像
FROM openjdk:11.0.4
# VOLUME 指定了临时文件目录为/tmp。
VOLUME /tmp
#复制jar包到镜像中，并且将名字改成app.jar
ADD question-bank.jar question-bank.jar
#在容器启动的时候运行命令，来启动我们的项目（这其实就是一段Linux命令）
ENTRYPOINT ["java","-jar","/question-bank.jar"]