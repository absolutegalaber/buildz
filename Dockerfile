FROM frolvlad/alpine-oraclejdk8:slim
ADD target/buildz-1.0-SNAPSHOT.jar /buildz-1.0-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "/buildz-1.0-SNAPSHOT.jar"]