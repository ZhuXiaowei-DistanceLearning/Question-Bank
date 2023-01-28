cd /home

APP_FILE=app.jar


if [ -f "$APP_FILE" ]
then
   exec java \
   -server -jar $APP_FILE --spring.profiles.active=${ENV-prod} --logging.level.root=${LOG_LEVEL-info} --server.port=${PROT-8080} --project.name=sentinel-dashboard
else
    echo "I haven't found the app.jar"
fi