FROM amazoncorretto:17-alpine-jdk
COPY target/*.jar warehouse.jar
ENTRYPOINT ["java","-jar","warehouse.jar"]