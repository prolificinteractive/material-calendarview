plugins {
    id("com.android.application")
}

android {
    compileSdk = AndroidProject.compileSdkVersion

    defaultConfig {
        applicationId = "com.prolificinteractive.materialcalendarview.sample"

        minSdk = AndroidProject.minSdkVersion
        targetSdk = AndroidProject.targetSdkVersion

        versionCode = 1
        versionName = "1.0"
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
    implementation(project(":library"))
    coreLibraryDesugaring(Dependency.desugarJdkLibs)
    implementation(Dependency.AndroidX.appcompat)
    implementation(Dependency.AndroidX.recyclerView)
    implementation(Dependency.Butterknife.core)
    annotationProcessor(Dependency.Butterknife.compiler)
}