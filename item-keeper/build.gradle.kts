import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
}

group = "com.cds"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot", "spring-boot-starter-data-jpa")
    implementation("org.springframework.boot", "spring-boot-starter-graphql")
    implementation("com.expediagroup", "graphql-kotlin-spring-server", "6.6.0")
    implementation("org.springframework.boot", "spring-boot-starter-quartz")
    implementation("org.springframework.boot", "spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module", "jackson-module-kotlin")
    implementation("org.jetbrains.kotlin", "kotlin-reflect")
    runtimeOnly("io.micrometer", "micrometer-registry-prometheus")
    runtimeOnly("org.postgresql", "postgresql")
    testImplementation("org.springframework.boot", "spring-boot-starter-test")
    testImplementation("org.springframework", "spring-webflux")
    testImplementation("org.springframework.graphql", "spring-graphql-test")
    implementation("org.springframework.boot", "spring-boot-starter-actuator")
    implementation("com.rometools", "rome", "2.1.0")
    implementation("org.json", "json", "20231013")
    implementation("org.glassfish", "javax.json", "1.1.2")
    implementation("com.google.code.gson", "gson","2.9.1")
    implementation("io.hypersistence", "hypersistence-utils-hibernate-63","3.7.3")
    implementation("com.graphql-java", "graphql-java-extended-scalars", "21.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
