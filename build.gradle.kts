import com.vanniktech.maven.publish.MavenPublishPluginExtension
import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(kotlin("gradle-plugin", "1.5.30"))
        classpath("com.vanniktech:gradle-maven-publish-plugin:0.17.0")
    }
}

repositories {
    google()
    mavenCentral()
}

allprojects {
    pluginManager.withPlugin("com.vanniktech.maven.publish") {
        extensions.getByType(MavenPublishPluginExtension::class.java).apply {
            sonatypeHost = SonatypeHost.S01
        }
    }
}
