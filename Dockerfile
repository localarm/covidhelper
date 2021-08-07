FROM postgres:13 as database
COPY src/main/resources/schema.sql /docker-entrypoint-initdb.d/1.sql
COPY src/main/resources/data.sql /docker-entrypoint-initdb.d/2.sql

FROM openjdk:11 as jar
COPY build/libs/demo-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]