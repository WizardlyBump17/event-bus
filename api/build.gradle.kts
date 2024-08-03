apply(plugin = "io.freefair.lombok")

version = "0.1.3"

val lombok = "1.18.34"
val jetbrainsAnnotations = "24.1.0"

dependencies {
    compileOnly("org.projectlombok:lombok:${lombok}")
    annotationProcessor("org.projectlombok:lombok:${lombok}")
    testCompileOnly("org.projectlombok:lombok:${lombok}")
    testAnnotationProcessor("org.projectlombok:lombok:${lombok}")

    implementation("org.jetbrains:annotations:${jetbrainsAnnotations}")
}

tasks {
    javadoc {
        options {
            dependsOn(getTasksByName("delombok", false))
            this as StandardJavadocDocletOptions
            addStringOption("link", "https://projectlombok.org/api/")
            addStringOption("link ", "https://javadoc.io/doc/org.jetbrains/annotations/${jetbrainsAnnotations}/")
        }
    }
}