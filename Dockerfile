FROM openjdk:17
ADD target/blog-app-docker.jar blog-app-docker.jar
ENTRYPOINT ["java", "-jar","/blog-app-docker.jar"]