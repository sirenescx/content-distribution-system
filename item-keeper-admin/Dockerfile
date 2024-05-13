FROM --platform=linux/amd64 amazoncorretto:17-alpine-jdk AS builder

WORKDIR /itemkeeperadmin

COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY gradlew .
COPY gradle ./gradle
COPY src ./src

RUN ./gradlew clean build -x test

FROM --platform=linux/amd64 amazoncorretto:17-alpine-jdk

WORKDIR /itemkeeperadmin

COPY --from=builder /itemkeeperadmin/build/libs/*.jar itemkeeperadmin.jar

EXPOSE 8081

CMD ["java", "-jar", "itemkeeperadmin.jar"]
