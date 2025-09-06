plugins {
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
    id("java")
    jacoco
    id("org.owasp.dependencycheck") version "9.1.0"
    id("org.cyclonedx.bom") version "1.7.4"
    id("com.github.spotbugs") version "6.0.7"
    id("info.solidsoft.pitest") version "1.15.0"
    checkstyle
    pmd
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")

    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    implementation("com.bucket4j:bucket4j-core:8.4.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.rest-assured:rest-assured:5.4.0")
    testImplementation("net.jqwik:jqwik:1.8.2")
    testImplementation("com.code-intelligence:jazzer-junit:0.24.0")

    spotbugs("com.github.spotbugs:spotbugs:4.8.4")
    spotbugsPlugins("com.h3xstream.findsecbugs:findsecbugs-plugin:1.13.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

jacoco {
    toolVersion = "0.8.11"
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

tasks.withType<Jar> {
    isPreserveFileTimestamps = false
    isReproducibleFileOrder = true
}

spotbugs {
    ignoreFailures.set(true)
    toolVersion.set("4.8.4")
    effort.set(com.github.spotbugs.snom.Effort.MAX)
    reportLevel.set(com.github.spotbugs.snom.Confidence.LOW)
}

pmd {
    isConsoleOutput = true
}

checkstyle {
    toolVersion = "10.12.4"
}

dependencyCheck {
    failOnError = false
}

pitest {
    targetClasses.set(listOf("com.cyberunited.secapi.*"))
    junit5PluginVersion.set("1.2.0")
    failWhenNoMutations.set(false)
}
