FROM adoptopenjdk/openjdk11:alpine-slim
RUN mkdir /opt/app
COPY ./target/scala-2.13/Akka-web-service-assembly-0.1.jar /opt/app
EXPOSE 3997
CMD ["java", "-jar", "/opt/app/Akka-web-service-assembly-0.1.jar"]