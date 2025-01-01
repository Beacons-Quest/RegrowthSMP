dependencies {
    // Dependencies
    compileOnly(files("../../libraries/Animatronicsplugin.jar"))
    compileOnly(files("../../libraries/ExcellentCrates-5.3.2.jar"))
    compileOnly("com.github.nulli0n:nightcore-spigot:v2.6.3-updated") {
        exclude("org.spigotmc")
    }
}
