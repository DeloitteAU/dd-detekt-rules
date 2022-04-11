import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.21"
    `maven-publish`
}

group = "com.dd.detektcustomrules"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("io.gitlab.arturbosch.detekt:detekt-api:1.19.0")

    testImplementation("io.gitlab.arturbosch.detekt:detekt-test:1.19.0")
    testImplementation("com.google.truth:truth:1.1.3")
    testImplementation("junit:junit:4.13.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

tasks.withType<Test>().configureEach {
    val compileSnippetText: Boolean = if (project.hasProperty("compile-test-snippets")) {
        (project.property("compile-test-snippets") as String).toBoolean()
    } else {
        false
    }
    systemProperty("compile-snippet-tests", compileSnippetText)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}
