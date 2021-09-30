import groovy.lang.MissingPropertyException
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.extra
import java.io.File
import java.util.Properties

plugins {
    id("com.android.library")
    id("maven-publish")
    id("signing")
}

android {
    compileSdk = AndroidProject.compileSdkVersion
    buildToolsVersion = AndroidProject.buildToolsVersion

    defaultConfig {
        minSdk = AndroidProject.minSdkVersion
        targetSdk = AndroidProject.targetSdkVersion
    }

    lint {
        isAbortOnError = false
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = AndroidProject.jvmVersion
        targetCompatibility = AndroidProject.jvmVersion
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
    description.set(project.requireProperty("publication.description"))
    url.set(project.requireProperty("publication.url"))
    licenses {
        license {
            name.set(project.requireProperty("publication.license.name"))
            url.set(project.requireProperty("publication.license.url"))
        }
    }
    developers {
        developer {
            name.set(project.requireProperty("publication.developer.name"))
            email.set(project.requireProperty("publication.developer.email"))
            organization.set(project.requireProperty("publication.developer.email"))
            organizationUrl.set(project.requireProperty("publication.developer.email"))
        }
    }
    scm {
        connection.set(project.requireProperty("publication.scm.connection"))
        developerConnection.set(project.requireProperty("publication.scm.developerConnection"))
        url.set(project.requireProperty("publication.scm.url"))
    }
}

signing {
    sign(publishing.publications)
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