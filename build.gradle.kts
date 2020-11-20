group = "me.jakejmattson"
version = "2.0.0"

plugins {
    java
    id("com.github.ben-manes.versions") version "0.36.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.seleniumhq.selenium:selenium-java:3.141.59")
    implementation("io.github.bonigarcia:webdrivermanager:4.2.2")
}