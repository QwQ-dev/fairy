plugins {
    id("io.fairyproject.module.bukkit")
}

dependencies {
    api("io.fairyproject:core-command")
    api("io.fairyproject:mc-locale")
    api(project(":bukkit-storage"))
}