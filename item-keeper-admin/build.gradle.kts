import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.expediagroup.graphql.plugin.gradle.graphql

plugins {
	id("org.springframework.boot") version "3.2.2"
	id("io.spring.dependency-management") version "1.1.4"
	id("com.expediagroup.graphql") version "7.0.0"
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

graphql {
	client {
		endpoint = "http://localhost:8080/graphql"
		packageName = "com.cds.generated"
	}
}

dependencies {
	implementation("org.springframework.boot", "spring-boot-starter-graphql")
	implementation("com.expediagroup", "graphql-kotlin-spring-client", "7.0.0")
	implementation("org.springframework.boot", "spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module", "jackson-module-kotlin")
	runtimeOnly("io.micrometer", "micrometer-registry-prometheus")
	testImplementation("org.springframework.boot", "spring-boot-starter-test")
	testImplementation("org.springframework", "spring-webflux")
	testImplementation("org.springframework.graphql", "spring-graphql-test")
	implementation("org.springdoc", "springdoc-openapi-starter-webmvc-ui", "2.3.0")
	implementation("org.springframework.boot", "spring-boot-starter-actuator")
	implementation("org.json", "json", "20231013")
	implementation("org.glassfish", "javax.json", "1.1.2")

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


