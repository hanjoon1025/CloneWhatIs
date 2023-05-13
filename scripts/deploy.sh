##!/usr/bin/env bash
#
##REPOSITORY=/home/ubuntu/Clone_What_Is
##cd $REPOSITORY
#
#APP_NAME=Clone_What_Is
#JAR_NAME=$(find . -name "*.jar" | sort | tail -n 1)
#JAR_PATH=./$JAR_NAME
#CURRENT_PID=$(pgrep -f $APP_NAME)
## JAR 파일에 실행 권한 설정
#sudo chmod +x "$JAR_PATH"
#
#if [ -z "$CURRENT_PID" ]; then
#echo "> 종료할 것 없음."
#else
#echo "> kill -15 $CURRENT_PID"
#kill -15 $CURRENT_PID
#sleep 5
#fi
#
#echo "> $JAR_PATH 배포"
#nohup java -jar $JAR_PATH > output.log 2>&1 &

#!/usr/bin/env bash

#BUILD_PATH=$(ls /home/ubuntu/Clone_What_Is/build/libs/*.jar)
#JAR_NAME=$(basename "$BUILD_PATH")
#echo "> 빌드 파일명: $JAR_NAME"
#
#DEPLOY_PATH=/home/ubuntu/Clone_What_Is/
#echo "> 빌드 파일 복사"
#cp "$BUILD_PATH" "$DEPLOY_PATH"
#
#APPLICATION_JAR_NAME=Clone_What_Is.jar
#APPLICATION_JAR="$DEPLOY_PATH$APPLICATION_JAR_NAME"
#
## JAR 파일에 실행 권한 설정
#sudo chmod +x "$APPLICATION_JAR"
#
#echo "> 현재 실행 중인 애플리케이션 PID 확인"
#CURRENT_PID=$(pgrep -f "$APPLICATION_JAR_NAME")
#
#if [ -z "$CURRENT_PID" ]; then
#  echo "> 종료할 애플리케이션이 없습니다."
#else
#  echo "> kill -15 $CURRENT_PID"
#  kill -15 "$CURRENT_PID"
#  sleep 5
#fi
#
#echo "> $APPLICATION_JAR 배포"
#nohup java -jar "$APPLICATION_JAR" > output.log 2>&1 &

#!/usr/bin/env bash

REPOSITORY=/home/ubuntu/Clone_What_Is
cd $REPOSITORY

APP_NAME=Clone_What_Is
JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep 'SNAPSHOT.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME

# JAR 파일에 실행 권한 설정
sudo chmod +x "$JAR_PATH"

CURRENT_PID=$(pgrep -f $APP_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 종료할것 없음."
else
  echo "> kill -9 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> $JAR_PATH 배포"
nohup java -jar $JAR_PATH > /dev/null 2> /dev/null < /dev/null &
