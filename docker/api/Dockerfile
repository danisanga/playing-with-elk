FROM gradle:8.6.0-jdk17-alpine AS build
# TODO - Fix PATH (../../), it's failing since I move Docker files to this specific directory...
COPY --chown=gradle:gradle ../../ /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean build -x test --no-daemon

FROM openjdk:17.0-jdk
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/ /app
ENTRYPOINT ["java","-jar","/app/playing-with-elk-1.0.0.jar"]