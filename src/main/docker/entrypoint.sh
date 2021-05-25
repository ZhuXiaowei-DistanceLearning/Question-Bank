#APP_FILE=/home/app.jar
#
#if [ -f "$APP_FILE" ]
#then
#    java -server $JAVA_OPTS -jar $APP_FILE --spring.profiles.active=${ENV-prod} --logging.level.root=${LOG_LEVEL-info}
#else
#    echo "I haven't found the app.jar"
#fi