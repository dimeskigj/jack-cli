plugins {
    kotlin("jvm") version "2.1.20"
    kotlin("plugin.serialization") version "2.1.20"
    application
    id("com.diffplug.spotless") version "7.0.0.BETA4"
    id("org.graalvm.buildtools.native") version "0.10.4"
}

group = "org.jack"
version = project.properties["version"] as String

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk:1.13.13")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")
    implementation("com.github.ajalt.clikt:clikt:5.0.3")
    implementation("com.github.ajalt.clikt:clikt-markdown:5.0.3")
    implementation("io.github.g0dkar:qrcode-kotlin:4.4.1")
    implementation("com.aallam.ulid:ulid-kotlin:1.3.0")
    implementation("com.cronutils:cron-utils:9.2.1")
}

tasks.test {
    useJUnitPlatform()
}

configurations {
    compileClasspath {
        resolutionStrategy.activateDependencyLocking()
    }
}

kotlin {
    jvmToolchain(21)
}

dependencyLocking {
    lockAllConfigurations()
}

application {
    mainClass.set("org.jack.MainKt")
}

graalvmNative {
    binaries {
        named("main") {
            imageName.set("jack")
            mainClass.set("org.jack.MainKt")
            fallback.set(false)
            buildArgs.add("--enable-url-protocols=https")
            resources {
                bundles.add("com.cronutils.CronUtilsI18N")
            }
        }
    }
    toolchainDetection.set(true)
}

spotless {
    kotlin {
        target("src/**/*.kt")
        ktlint()
    }
    kotlinGradle {
        target("*.gradle.kts")
        ktlint()
    }
}

tasks.processResources {
    filesMatching("version.properties") {
        expand(project.properties)
    }
}
