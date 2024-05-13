FROM --platform=linux/amd64 amazoncorretto:17-alpine-jdk AS builder

WORKDIR /itemkeeperadmin

COPY item-keeper-admin/build.gradle.kts .
COPY item-keeper-admin/settings.gradle.kts .
COPY item-keeper-admin/gradlew .
COPY item-keeper-admin/gradle ./gradle
COPY item-keeper-admin/src ./src

RUN ./gradlew clean build -x test

FROM --platform=linux/amd64 amazoncorretto:17-alpine-jdk

WORKDIR /itemkeeperadmin

COPY --from=builder /itemkeeperadmin/build/libs/*.jar itemkeeperadmin.jar

EXPOSE 8081

CMD ["java", "-jar", "itemkeeperadmin.jar"]
