plugins {
    val kotlinVersion = "1.9.25"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("kapt") version kotlinVersion

    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.pjt"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
}

val flywayVersion = "10.4.1"
val queryDslVersion = "5.0.0"
val loggerVersion = "5.1.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    /** Flyway */
    implementation("org.flywaydb:flyway-core:$flywayVersion")
    implementation("org.flywaydb:flyway-mysql:$flywayVersion")

    /** QueryDSL */
    implementation("com.querydsl:querydsl-jpa:$queryDslVersion:jakarta")
    kapt("com.querydsl:querydsl-apt:$queryDslVersion:jakarta")

    /** Logging */
    implementation("io.github.oshai:kotlin-logging-jvm:$loggerVersion")

    /** DB */
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.h2database:h2")

    /** Test */
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

kotlin {
    jvmToolchain(21)
}

val queryDslDir = layout.buildDirectory.dir("generated/querydsl").get().asFile
sourceSets["main"].kotlin.srcDir(queryDslDir)

tasks.withType<JavaCompile> {
    options.generatedSourceOutputDirectory.set(queryDslDir)
}

tasks.named("clean") {
    doLast {
        queryDslDir.deleteRecursively()
    }
}

kapt {
    correctErrorTypes = true
}

tasks.test {
    useJUnitPlatform()
}
