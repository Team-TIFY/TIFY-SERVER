FROM amazoncorretto:17
ARG JAR_FILE=./Api/build/libs/Api.jar
COPY ${JAR_FILE} app.jar

ARG PROFILE=dev
ENV PROFILE=${PROFILE}
ENTRYPOINT ["java","-Duser.timezone=Asia/Seoul", "-Dspring.profiles.active=${PROFILE}", "-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
