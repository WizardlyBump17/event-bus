plugins {
    id("java")
    id("io.freefair.lombok") version "8.6" apply false
}

allprojects {
    apply(plugin = "java")
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
    }
}
