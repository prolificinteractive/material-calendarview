import org.gradle.api.JavaVersion

object AndroidProject {
    const val buildToolsVersion = "31.0.0"
    const val minSdkVersion = 21
    const val compileSdkVersion = 31
    const val targetSdkVersion = compileSdkVersion
    val jvmVersion = JavaVersion.VERSION_1_8
}
