FROM amazoncorretto:17
ARG JAR_FILE=target/scaling-winner-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} scaling-winner-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/scaling-winner-0.0.1-SNAPSHOT.jar"]