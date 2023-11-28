plugins {
    java
    id("org.springframework.boot") version "3.1.5"
    id("io.spring.dependency-management") version "1.1.3"
}

group = "be.kdg.programming3"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("com.fazecast:jSerialComm:2.10.4")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.webjars:bootstrap:5.3.2")
    implementation("org.webjars:webjars-locator-core:0.48")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation ("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework:spring-websocket:6.1.0")
    implementation("org.springframework:spring-messaging:6.1.0")

    //for web socket communication
    implementation("org.springframework.boot:spring-boot-starter-websocket:3.1.5")
    // jpa library
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.1.5")
    runtimeOnly ("com.h2database:h2")

//    implementation('org.springframework.security:spring-security-test')
    implementation("org.springframework.security:spring-security-core:6.2.0")

}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.bootBuildImage {
    builder.set("paketobuildpacks/builder-jammy-base:latest")
}
