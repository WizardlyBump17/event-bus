version = "0.1.0"

val lombok = "1.18.34"
val jetbrainsAnnotations = "24.1.0"

dependencies {
    compileOnly("org.projectlombok:lombok:${lombok}")
    annotationProcessor("org.projectlombok:lombok:${lombok}")
    testCompileOnly("org.projectlombok:lombok:${lombok}")
    testAnnotationProcessor("org.projectlombok:lombok:${lombok}")

    implementation("org.jetbrains:annotations:${jetbrainsAnnotations}")
}