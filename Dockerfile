FROM openjdk:11
ADD target/docker-spring-boot.jar docker-spring-boot.jar
EXPOSE 8083
ENTRYPOINT  ["java", "-jar", "docker-spring-boot.jar"]
# COPY . /usr/src/myapp
# WORKDIR /usr/src/myapp
# RUN javac Main.java
# CMD ["java", "Main"]