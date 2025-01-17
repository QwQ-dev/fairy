import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.`maven-publish`

plugins {
    `maven-publish`
}

var fairyUrl = "https://github.com/FairyProject/fairy"

afterEvaluate {
    publishing {
        publications {
            if (!plugins.hasPlugin("org.gradle.java-gradle-plugin")) {
                create<MavenPublication>("maven") {
                    if (project.plugins.hasPlugin("org.gradle.java-platform")) {
                        from(components["javaPlatform"])
                    } else {
                        from(components["java"])
                    }

                    versionMapping {
                        usage("java-runtime") {
                            fromResolutionResult()
                        }
                    }

                    pom {
                        name = "Fairy " + project.name
                        version = project.version.toString()
                        description = project.description

                        url = fairyUrl
                        organization {
                            name = "Fairy Project"
                            url = fairyUrl
                        }
                        licenses {
                            license {
                                name = "MIT License"
                                url = "https://www.apache.org/licenses/LICENSE-2.0"
                                distribution = "repo"
                            }
                        }
                        scm {
                            url = fairyUrl
                            connection = "scm:git:git://github.com/FairyProject/fairy"
                            developerConnection = "scm:git:git://github.com/FairyProject/fairy"
                        }
                        developers {
                            developer {
                                id = "leegod"
                                name = "LeeGod"
                                email = "leegod@imanity.dev"
                            }
                        }
                        issueManagement {
                            system = "GitHub"
                            url = "$fairyUrl/issues"
                        }
                    }
                }
            }
        }

        repositories {
            maven {
                name = "Production"
                url = uri("https://repo.imanity.dev/imanity-libraries/")
                credentials {
                    username = findProperty("imanityLibrariesUsername").toString()
                    password = findProperty("imanityLibrariesPassword").toString()
                }
            }
        }
    }
}