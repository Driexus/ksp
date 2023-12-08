plugins {
    id("com.google.devtools.ksp")
    kotlin("jvm")
}

version = "1.0-SNAPSHOT"

dependencies {
    implementation("junit:junit:4.13.1")
    implementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib"))
    implementation(project(":test-processor"))
    ksp(project(":test-processor"))

    implementation(platform("com.google.firebase:firebase-bom:32.6.0"))
    implementation("com.google.firebase:firebase-firestore-ktx")


}

ksp {
    arg("option1", "value1")
    arg("option2", "value2")
}

tasks.test {
    useJUnitPlatform()
}
