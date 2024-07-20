plugins {
    id("java")
    id("io.freefair.lombok") version "8.6" apply false
    id("maven-publish")
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")
}

subprojects {
    group = "com.wizardlybump17.event-bus"

    repositories {
        mavenCentral()
    }

    val junit = "5.10.3"

    dependencies {
        testImplementation(platform("org.junit:junit-bom:${junit}"))
        testImplementation("org.junit.jupiter:junit-jupiter:${junit}")
    }

    tasks {
        test {
            useJUnitPlatform()
            testLogging {
                events("passed", "skipped", "failed", "standardOut", "standardError")
            }
        }

        build {
            dependsOn(test)
        }

        compileJava {
            options.encoding = Charsets.UTF_8.name()
            options.release.set(17)
        }

        java {
            withSourcesJar()
        }

        javadoc {
            options {
                this as StandardJavadocDocletOptions
                addBooleanOption("Xdoclint:none", true)
                addStringOption("Xmaxwarns", "1")
            }
        }

        publishing {
            repositories {
                maven {
                    url = uri("https://maven.pkg.github.com/WizardlyBump17/event-bus")
                    credentials {
                        username = (project.findProperty("gpr.user") ?: System.getenv("USERNAME")) as? String
                        password = (project.findProperty("gpr.key") ?: System.getenv("TOKEN")) as? String
                    }
                }
            }

            publications {
                create<MavenPublication>("maven") {
                    from(project.components["java"])
                }
            }
        }
    }
}
