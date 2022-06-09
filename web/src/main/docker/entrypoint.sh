cd /home

APP_FILE=app.jar
SW_AGENT_NAME=${ENV-prod}-${APP_NAME-ApecSpringbootServer}
SW_AGENT_URL=${SKYWALKING_AGENT-127.0.0.1:11800}
SW_AGENT_HOST=${SW_AGENT_URL%:*}
SW_AGENT_PORT=${SW_AGENT_URL#*:}



if [ -f "$APP_FILE" ]
then
   exec java \
   -javaagent:/home/agent/skywalking-agent.jar
   -Dskywalking.agent.service_name=${SW_AGENT_NAME}
   -Dskywalking.collector.backend_service=${SW_AGENT_URL}\
   -Dskywalking.plugin.toolkit.log.grpc.reporter.server_host=${SW_AGENT_HOST} \
   -Dskywalking.plugin.toolkit.log.grpc.reporter.server_port=${SW_AGENT_PORT} \
   -Dskywalking.plugin.toolkit.log.grpc.reporter.max_message_size=10485760 \
   -Dskywalking.plugin.toolkit.log.grpc.reporter.upstream_timeout=30 \
   -server $JAVA_OPTS -jar $APP_FILE --spring.profiles.active=${ENV-prod} --logging.level.root=${LOG_LEVEL-info} ${SERVERPORT}
else
    echo "I haven't found the app.jar"
fi