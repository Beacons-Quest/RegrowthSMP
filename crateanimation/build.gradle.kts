//dependencies {
//    // Dependencies
//    compileOnly("io.papermc.paper:paper-api:${findProperty("minecraftVersion")}-R0.1-SNAPSHOT")
//
//    // Libraries
//    implementation("org.lushplugins:LushLib:${findProperty("lushLibVersion")}")
//}

tasks {
    shadowJar {
        minimize()

        archiveFileName.set("${project.name}-${project.version}.jar")
    }
}