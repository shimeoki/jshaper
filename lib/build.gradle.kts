plugins {
    id("base")
    id("java-library")
    id("java-library-distribution")
}

version = "0.7.0"

base {
    archivesName = rootProject.name
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.3")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }

    withJavadocJar()
    withSourcesJar()
}

distributions {
    main {
        distributionBaseName = rootProject.name
    }
}

tasks.test {
    useJUnitPlatform()
}
