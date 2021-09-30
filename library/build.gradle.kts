import groovy.lang.MissingPropertyException
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.extra
import java.io.File
import java.util.Properties

plugins {
    id("com.android.application")
    id("maven-publish")
}

android {
    compileSdk = AndroidProject.compileSdkVersion
    buildToolsVersion = AndroidProject.buildToolsVersion

    defaultConfig {
        minSdk = AndroidProject.minSdkVersion
        targetSdk = AndroidProject.targetSdkVersion

        versionCode = 20
        versionName = "2.1.0"
    }

    lint {
        isAbortOnError = false
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = AndroidProject.jvmVersion
        targetCompatibility = AndroidProject.jvmVersion
    }

    buildTypes {
        release { isMinifyEnabled = true }
    }
}

dependencies {
    coreLibraryDesugaring(Dependency.desugarJdkLibs)
    implementation(Dependency.AndroidX.appcompat)
    implementation(Dependency.AndroidX.material)
    implementation(Dependency.AndroidX.annotation)

    testImplementation(Dependency.Test.junit)
    testImplementation(Dependency.Test.Hamcrest.core)
    testImplementation(Dependency.Test.Hamcrest.library)
    testImplementation(Dependency.Test.Hamcrest.integration)

    androidTestImplementation(Dependency.Test.junit)
    androidTestImplementation(Dependency.Test.AndroidX.rules)
    androidTestImplementation(Dependency.Test.AndroidX.runner)
}

fun Project.requireProperty(name: String): String =
    findProperty(name)?.toString() ?: throw MissingPropertyException("Not found property with name: $name")

fun Project.localProperties(): Properties {
    val local = Properties()
    val localProperties: File = rootProject.file("local.properties")
    if (localProperties.exists()) {
        localProperties.inputStream().use { local.load(it) }
    }
    return local
}

val groupId: String = project.requireProperty(name = "publication.groupId")
val artifactId: String = project.requireProperty(name = "publication.artifactId")
val versionName: String = project.requireProperty(name = "publication.versionName")

project.group = groupId
project.version = versionName

val localProperties = project.localProperties()

extra["signing.keyId"] = localProperties.getProperty("publication.signing.keyId")
extra["signing.password"] = localProperties.getProperty("publication.signing.password")
extra["signing.secretKeyRingFile"] = "$rootDir/${localProperties.getProperty("publication.signing.secretKeyRingFileName")}"

publishing.repositories {
    maven {
        name = project.requireProperty(name = "publication.repository.name")
        setUrl(project.requireProperty("publication.repository.url"))
        credentials {
            username = localProperties.getProperty("publication.user.login")
            password = localProperties.getProperty("publication.user.password")
        }
    }
}

fun metadata(publication: MavenPublication) {
    publication.groupId = groupId
    publication.artifactId = artifactId
    publication.version = versionName
}

fun MavenPom.config() {
    name.set(groupId)
    licenses {
        license {
            name.set("The Apache Software License, Version 2.0")
            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            distribution.set("repo")
        }
    }
}

// Because the components are created only during the afterEvaluate phase, you must
// configure your publications using the afterEvaluate() lifecycle method.
afterEvaluate {
    publishing {
        publications {
            create("publishAndroid", MavenPublication::class.java) {
                from(project.components.getByName("release"))
                metadata(this)
                pom { config() }
            }
        }
    }
}