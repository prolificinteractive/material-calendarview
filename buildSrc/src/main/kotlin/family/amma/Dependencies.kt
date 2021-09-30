@file:JvmMultifileClass

private const val kotlinVersion = "1.5.31"

object BuildPlugin {
    const val gradle = "com.android.tools.build:gradle:7.0.2"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
}

object Dependency {
    const val desugarJdkLibs = "com.android.tools:desugar_jdk_libs:1.1.5"

    object AndroidX {
        const val material = "com.google.android.material:material:1.4.0"
        const val annotation = "androidx.annotation:annotation:1.2.0"
        const val appcompat = "androidx.appcompat:appcompat:1.3.1"
        const val recyclerView = "androidx.recyclerview:recyclerview:1.2.1"
    }

    object Butterknife {
        private const val version = "10.2.3"
        const val core = "com.jakewharton:butterknife:$version"
        const val compiler = "com.jakewharton:butterknife-compiler:$version"
    }

    object Test {
        const val junit = "junit:junit:4.13.2"

        object Hamcrest {
            private const val version = "2.2"

            const val core = "org.hamcrest:hamcrest-core:$version"
            const val library = "org.hamcrest:hamcrest-library:$version"
            const val integration = "org.hamcrest:hamcrest-integration:1.3"
        }

        object AndroidX {
            private const val runnerVersion = "1.4.0"

            const val runner = "androidx.test:runner:$runnerVersion"
            const val rules = "androidx.test:rules:$runnerVersion"
        }
    }
}
