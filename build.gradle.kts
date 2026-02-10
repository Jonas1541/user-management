plugins {
    java
    id("org.springframework.boot") version "4.0.2"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.jonasdurau"
version = "0.0.1-SNAPSHOT"
description = "A cloud-native user management system using Spring Boot, Thymeleaf and HTMX."

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Core Web & Data
    implementation("org.springframework.boot:spring-boot-starter-web") // Inclui Tomcat + MVC
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    
    // Frontend Engine (Thymeleaf + Layouts + HTMX)
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect") // Essencial para layouts
    implementation("org.webjars.npm:htmx.org:1.9.10") // O HTMX via WebJar

    // Database
    runtimeOnly("org.postgresql:postgresql")

    // Developer Experience
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose") // Integração nativa com seu compose.yaml

    // Testing (Suite completa)
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}