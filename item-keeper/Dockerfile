FROM --platform=linux/amd64 amazoncorretto:17-alpine-jdk AS builder

WORKDIR /itemkeeper

COPY item-keeper/build.gradle.kts .
COPY item-keeper/settings.gradle.kts .
COPY item-keeper/gradlew .
COPY item-keeper/gradle ./gradle
COPY item-keeper/src ./src

RUN ./gradlew clean build -x test

FROM --platform=linux/amd64 amazoncorretto:17-alpine-jdk

WORKDIR /itemkeeper

COPY --from=builder /itemkeeper/build/libs/*.jar itemkeeper.jar
COPY --from=builder /itemkeeper/src/main/resources/mapping_configs mapping_configs

EXPOSE 8080

CMD ["java", "-jar", "itemkeeper.jar"]
