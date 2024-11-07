plugins {
    id("java")
    id("com.gradleup.shadow") version "8.3.0"
}

group = "dev.larrox"
version = "1.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.minestom:minestom-snapshots:f71ab6d851")
    implementation("org.slf4j:slf4j-simple:2.0.16")
    implementation("de.articdive:jnoise-pipeline:4.1.0")
}

tasks.test {
    useJUnitPlatform()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks {
    jar {
        manifest {
            attributes["Main-Class"] = "dev.larrox.Main"
        }
    }

    build {
        dependsOn(shadowJar)
    }
    shadowJar {
        mergeServiceFiles()
        archiveClassifier.set("")
    }
}