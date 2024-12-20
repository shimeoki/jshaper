plugins {
    id("base")
    `kotlin-dsl`

    id("java-library")
    id("java-library-distribution")

    id("io.deepmedia.tools.deployer") version "0.15.0"
}

group = "io.github.shimeoki"
version = "0.9.7"

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

deployer {
    content {
        component {
            fromJava()
        }
    }

    projectInfo {
        name.set(rootProject.name)
        description.set("A 3D model parsing library for Java.")
        url.set("https://github.com/shimeoki/jshaper")

        artifactId.set(rootProject.name)

        scm {
            fromGithub("shimeoki", "jshaper")
        }

        license(MIT)

        developer("shimeoki", "shimeoki@gmail.com")
    }

    localSpec("m2") {
    }

    localSpec("artifact") {
        directory.set(file("build/artifact"))
    }

    centralPortalSpec {
        auth.user.set(secret("CENTRAL_PORTAL_USERNAME"))
        auth.password.set(secret("CENTRAL_PORTAL_PASSWORD"))

        signing {
            key.set(secret("GPG_KEY"))
            password.set(secret("GPG_PWD"))
        }
    }

    githubSpec {
        owner.set("shimeoki")
        repository.set("jshaper")

        auth.user.set(secret("GITHUB_ACTOR"))
        auth.token.set(secret("GITHUB_TOKEN"))
    }
}

tasks.test {
    useJUnitPlatform()
}
