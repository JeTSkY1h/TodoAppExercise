FROM openjdk:17

ENV ENVIRONMENT=prod

LABEL JeTSky1h="jetsky1h@googlemail.com"

ADD target/Quiz-0.0.1-SNAPSHOT.jar Quiz-0.0.1-SNAPSHOT.jar

CMD [ "sh", "-c", "java -jar /app.jar" ]