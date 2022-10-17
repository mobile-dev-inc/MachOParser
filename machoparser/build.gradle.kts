plugins {
    `maven-publish`
    id("org.jetbrains.kotlin.jvm")
    `java-library`
    id("com.palantir.graal") version "0.9.0"
    id("org.graalvm.buildtools.native") version "0.9.5"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testImplementation("com.google.truth:truth:1.1.3")
}

tasks.test {
    useJUnitPlatform()
}
