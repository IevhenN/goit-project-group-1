plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
//    implementation("org.telegram:telegrambots:5.7.1")
//    implementation("org.jsoup:jsoup:1.14.3")
//    implementation("com.google.code.gson:gson:2.9.0")
//    implementation("org.projectlombok:lombok:1.18.22")
//    compileOnly("org.projectlombok:lombok:0.11.0")
//    annotationProcessor("org.projectlombok:lombok:1.18.30")
//    implementation("org.telegram:telegrambotsextensions:5.7.1")

// https://mvnrepository.com/artifact/org.telegram/telegrambots
    implementation("org.telegram:telegrambots:6.9.7.1")

// https://mvnrepository.com/artifact/org.jsoup/jsoup
    implementation("org.jsoup:jsoup:1.17.2")
// https://mvnrepository.com/artifact/com.google.code.gson/gson
    implementation("com.google.code.gson:gson:2.10.1")

    implementation("org.projectlombok:lombok:1.18.22")
    compileOnly("org.projectlombok:lombok:0.11.0")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

// https://mvnrepository.com/artifact/org.telegram/telegrambotsextensions
    implementation("org.telegram:telegrambotsextensions:6.9.7.1")

}

tasks.test {
    useJUnitPlatform()
}