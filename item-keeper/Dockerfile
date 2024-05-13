FROM --platform=linux/amd64 amazoncorretto:17-alpine-jdk AS builder

WORKDIR /itemkeeper

COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY gradlew .
COPY gradle ./gradle
COPY src ./src

RUN ./gradlew clean build -x test

FROM --platform=linux/amd64 amazoncorretto:17-alpine-jdk

WORKDIR /itemkeeper

COPY --from=builder /itemkeeper/build/libs/*.jar itemkeeper.jar

EXPOSE 8080

CMD ["java", "-jar", "itemkeeper.jar"]
