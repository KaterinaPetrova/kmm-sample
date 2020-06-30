buildscript {
    repositories {
        google()
        jcenter()
        maven ("https://dl.bintray.com/kotlin/kotlin-eap")
        maven ("https://kotlin.bintray.com/kotlinx")
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4-M2")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.4-M2")
        classpath("com.android.tools.build:gradle:4.0.0")
    }
}
subprojects {
    repositories {
        google()
        jcenter()
        maven ("https://dl.bintray.com/kotlin/kotlin-eap")
    }
}
