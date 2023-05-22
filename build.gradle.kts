plugins {
    kotlin("jvm") version "1.8.21"
    id("io.papermc.paperweight.userdev") version "1.5.3"
    id("xyz.jpenilla.run-paper") version "2.0.1"
    id("maven-publish")
    //id("signing")
}

group = "tech.marlonr"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    paperweight.paperDevBundle("1.19.4-R0.1-SNAPSHOT")
    implementation("net.axay:kspigot:1.19.2")
}


val javaVersion = 17

tasks {
    assemble {
        dependsOn(reobfJar)
    }
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(javaVersion)
    }
    compileKotlin {
        kotlinOptions.jvmTarget = javaVersion.toString()
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }
}

kotlin {
    jvmToolchain(javaVersion)
}

/*
    signing {
    sign(publishing.publications)
}
 */

publishing {
    kotlin.runCatching {
        repositories {
            maven("https://maven.blueamethyst.me/releases") {
                name = "blueamethystRepoRelease"
                credentials(PasswordCredentials::class) {
                    username = System.getenv("reposilite.username")
                    password = System.getenv("reposilite.password")
                }
                authentication {
                    create<BasicAuthentication>("basic")
                }
            }
        }
    }.onFailure {
        println("Unable to add publishing repositories: ${it.message}")
    }

    publications {
        create<MavenPublication>(project.name) {
            from(components["java"])
            artifact(tasks.reobfJar)

            this.groupId = project.group.toString()
            this.artifactId = project.name.toLowerCase()
            this.version = version
        }
    }
}