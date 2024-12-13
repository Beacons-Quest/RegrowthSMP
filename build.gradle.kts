plugins {
    `java-library`
    `maven-publish`
    id("io.github.goooler.shadow") version("8.1.7")
    id("xyz.jpenilla.run-paper") version("2.2.4")
}

group = "org.lushplugins"
version = "1.0.3"

dependencies {
    // Libraries
    implementation("org.lushplugins:LushLib:0.10.24")

    // Modules
    implementation(project(":modules:cosmetics"))
    implementation(project(":modules:crateanimation"))
    implementation(project(":modules:recipes"))
    implementation(project(":modules:glassitemframes"))
    implementation(project(":modules:extraluckpermscontexts"))
}

tasks {
    shadowJar {
        relocate("org.lushplugins.lushlib", "org.lushplugins.regrowthsmp.libraries.lushlib")
        relocate("fr.skytasul", "org.lushplugins.regrowthsmp.libraries.skytasul")

        minimize()
    }

    runServer {
        minecraftVersion("1.21.1")

        downloadPlugins {
            modrinth("luckperms", "v5.4.145-bukkit")
            hangar("Floodgate", "Floodgate")
            github("nulli0n", "nightcore-spigot", "v2.6.3-updated", "nightcore-2.6.3.jar")
        }
    }
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "io.github.goooler.shadow")

    group = rootProject.group
    version = rootProject.version

    dependencies {
        compileOnly("org.lushplugins:LushLib:0.10.24")
    }
}

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://oss.sonatype.org/content/groups/public/")
        maven("https://repo.papermc.io/repository/maven-public/") // Paper
        maven("https://repo.lushplugins.org/releases/") // LushLib
        maven("https://repo.lushplugins.org/snapshots/") // LushLib
        maven("https://repo.opencollab.dev/main/") // Floodgate
        maven("https://jitpack.io/") // nightcore
    }

    dependencies {
        compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
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