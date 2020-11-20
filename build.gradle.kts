group = "me.jakejmattson"
version = "2.0.0"

plugins {
    java
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.seleniumhq.selenium:selenium-java:3.141.59")
    implementation("io.github.bonigarcia:webdrivermanager:3.7.1")
}