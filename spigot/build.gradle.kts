plugins {
    id("io.papermc.paperweight.userdev") version "1.7.1"
}

apply(plugin = "io.freefair.lombok")

version = "0.1.0"

val lombok = "1.18.34"
val jetbrainsAnnotations = "24.1.0"
val paper = "1.17.1-R0.1-20220414.034903-210"

dependencies {
    compileOnly("org.projectlombok:lombok:${lombok}")
    annotationProcessor("org.projectlombok:lombok:${lombok}")
    testCompileOnly("org.projectlombok:lombok:${lombok}")
    testAnnotationProcessor("org.projectlombok:lombok:${lombok}")

    implementation("org.jetbrains:annotations:${jetbrainsAnnotations}")

    implementation(project(":api"))

    paperweightDevelopmentBundle("io.papermc.paper:dev-bundle:${paper}")
}

tasks {
    javadoc {
        options {
            dependsOn(getTasksByName("delombok", false))
            this as StandardJavadocDocletOptions
            addStringOption("link", "https://projectlombok.org/api/")
            addStringOption("link ", "https://jd.papermc.io/paper/1.17/")
            addStringOption("link  ", "https://javadoc.io/doc/org.jetbrains/annotations/${jetbrainsAnnotations}/")
        }
    }
}