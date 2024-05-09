plugins {
    id("io.fairyproject.bootstrap")
}

dependencies {
    implementation(project(":core-bootstrap"))
    compileOnly("io.fairyproject:bukkit-platform")

    compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
    testImplementation("dev.imanity.mockbukkit:MockBukkit1.16:1.0.1")
    testImplementation("io.fairyproject:bukkit-tests")
}