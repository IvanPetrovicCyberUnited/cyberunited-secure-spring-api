import info.solidsoft.gradle.pitest.PitestPluginExtension

plugins {
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
    id("java")
    id("jacoco")
    id("pmd")
    id("checkstyle")
    id("com.github.spotbugs") version "6.0.9"
    id("org.owasp.dependencycheck") version "10.0.2"
    id("org.cyclonedx.bom") version "1.8.2"
}

buildscript {
    repositories { mavenCentral() }
    dependencies { classpath("info.solidsoft.gradle.pitest:gradle-pitest-plugin:1.15.0") }
}

apply(plugin = "info.solidsoft.pitest")

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
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.jsonwebtoken:jjwt-api:0.12.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.5")
    implementation("org.springframework.security:spring-security-crypto")
    implementation("com.bucket4j:bucket4j-core:8.4.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.rest-assured:rest-assured:5.4.0")
    testImplementation("com.code-intelligence:jazzer-junit:0.25.0")
    testImplementation("net.jqwik:jqwik:1.9.1")
}

tasks.withType<Test> {
    useJUnitPlatform()
    systemProperty("jazzer.semiStableApi", "true")
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

checkstyle {
    toolVersion = "10.12.4"
    configDirectory.set(file("config/checkstyle"))
}

pmd {
    toolVersion = "6.55.0"
    ruleSets = listOf()
    ruleSetFiles = files("config/pmd/pmd.xml")
}

spotbugs {
    effort.set(com.github.spotbugs.snom.Effort.DEFAULT)
    reportLevel.set(com.github.spotbugs.snom.Confidence.LOW)
    excludeFilter.set(file("config/spotbugs/exclude.xml"))
}

configure<PitestPluginExtension> {
    junit5PluginVersion.set("1.2.1")
    testPlugin.set("junit5")
    targetClasses.set(listOf("com.cyberunited.secureapi.*"))
}

dependencyCheck {
    failOnError = false
}

tasks.cyclonedxBom {
    includeConfigs.set(listOf("runtimeClasspath"))
    notCompatibleWithConfigurationCache("CycloneDX plugin not CC-ready")
}
