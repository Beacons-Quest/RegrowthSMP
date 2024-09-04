plugins {
    `java-library`
    `maven-publish`
    id("io.github.goooler.shadow") version("8.1.7")
    id("xyz.jpenilla.run-paper") version("2.2.4")
}

dependencies {
    // Dependencies
    compileOnly("io.papermc.paper:paper-api:${findProperty("minecraftVersion")}-R0.1-SNAPSHOT")

    // Libraries
    implementation("org.lushplugins:LushLib:${findProperty("lushLibVersion")}")

    // Modules
    implementation(project(":crateanimation"))
}

tasks {
    shadowJar {
        relocate("org.lushplugins.lushlib", "org.lushplugins.regrowthsmp.libraries.lushlib")

        minimize()

        val folder = System.getenv("pluginFolder")
        if (folder != null) {
            destinationDirectory.set(file(folder))
        }
        archiveFileName.set("${project.name}-${project.version}.jar")
    }

    runServer {
        minecraftVersion("1.21")

        downloadPlugins {
            file("libraries/Animatronicsplugin.jar")
            file("libraries/ExcellentCreates-5.3.2.jar")
            github("nulli0n", "nightcore", "v2.6.3-updated", "nightcore-2.6.3.jar")
        }
    }
}

allprojects {
    apply(plugin = "java-library")
    apply(plugin = "io.github.goooler.shadow")

    group = "org.lushplugins"
    version = "1.0.0-alpha1"

    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://oss.sonatype.org/content/groups/public/")
        maven("https://repo.papermc.io/repository/maven-public/") // Paper
        maven("https://repo.lushplugins.org/releases/") // LushLib
        maven("https://repo.lushplugins.org/snapshots/") // LushLib
        maven("https://jitpack.io/") // nightcore
    }

    dependencies {
        // Dependencies
        compileOnly("io.papermc.paper:paper-api:${findProperty("minecraftVersion")}-R0.1-SNAPSHOT")

        // Libraries
        implementation("org.lushplugins:LushLib:${findProperty("lushLibVersion")}")
    }

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(21))
    }

    tasks {
        withType<JavaCompile> {
            options.encoding = "UTF-8"
        }

        shadowJar {
            minimize()

            archiveFileName.set("${project.name}-${project.version}.jar")
        }

        processResources{
            expand(project.properties)

            inputs.property("version", rootProject.version)
            filesMatching("plugin.yml") {
                expand("version" to rootProject.version)
            }
        }
    }
}