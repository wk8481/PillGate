plugins {
    java
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "be.kdg.programming3"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

//dependencies {
//    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
//    implementation("org.springframework.boot:spring-boot-starter-validation")
//    implementation("org.springframework.boot:spring-boot-starter-web")
//    developmentOnly("org.springframework.boot:spring-boot-devtools")
//    runtimeOnly("com.h2database:h2")
//    testImplementation("org.springframework.boot:spring-boot-starter-test")
//    implementation("org.springframework.boot:spring-boot-starter-jdbc")
//    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
//    implementation("org.springframework.boot:spring-boot-starter-web")
//    testImplementation("org.springframework.boot:spring-boot-starter-test")
//    implementation("org.springframework.boot:spring-boot-starter-validation")
//    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//    runtimeOnly("com.h2database:h2")
//    implementation("com.fazecast:jSerialComm:2.10.4")
//    implementation("com.google.code.gson:gson:2.10.1")
//    implementation("org.webjars:bootstrap:5.3.2")
//    implementation("org.webjars:webjars-locator-core:0.48")
//}

dependencies{
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("com.google.code.gson:gson:2.9.1")
    implementation("org.webjars:webjars-locator-core:0.48")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation ("org.springframework.boot:spring-boot-starter-jdbc")
    runtimeOnly ("com.h2database:h2")
    implementation("com.fazecast:jSerialComm:2.10.4") //this for da serial
    implementation("org.webjars:bootstrap:5.3.2") //
    implementation("org.webjars:webjars-locator-core:0.48")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")


}

tasks.withType<Test> {
    useJUnitPlatform()
}
