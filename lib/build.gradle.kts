plugins {
    id("base")

    id("java-library")
    id("java-library-distribution")

    id("maven-publish")
    id("signing")
}

group = "io.github.shimeoki"
version = "0.9.5"

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

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = rootProject.name

            pom {
                name = rootProject.name
                description = "A 3D model parsing library for Java."
                url = "https://shimeoki.github.io/jshaper"

                licenses {
                    license {
                        name = "MIT License"
                        url = "https://github.com/shimeoki/jshaper/blob/main/LICENSE"
                    }
                }

                scm {
                    connection = "scm:git:git://github.com/shimeoki/jshaper.git"
                    developerConnection = "scm:git:ssh://github.com/shimeoki/jshaper.git"
                    url = "https://github.com/shimeoki/jshaper"
                }
            }

            from(components["java"])
        }
    }

    repositories {
        maven {
            name = "ossrh"
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")

            credentials {
                username = System.getenv("OSSRH_USERNAME")
                password = System.getenv("OSSRH_PASSWORD")
            }
        }
    }
}

signing {
    val signingKey: String? by project
    // env: ORG_GRADLE_PROJECT_signingKey

    val signingPassword: String? by project
    // env: ORG_GRADLE_PROJECT_signingPassword

    useInMemoryPgpKeys(signingKey, signingPassword)

    sign(publishing.publications["maven"])
}

tasks.test {
    useJUnitPlatform()
}
