FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar spring.jar
ENTRYPOINT ["java","-jar","/spring.jar"]
EXPOSE 8085