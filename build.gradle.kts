plugins {
    kotlin("js") version "1.5.31"
}

group = "com.mistersourcerer.games"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(devNpm("node-sass", "^6.0.1"))
    implementation(devNpm("sass-loader", "^12.1.0"))
}

kotlin {
    js(LEGACY) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
}

tasks.register<Copy>("sass"){
    from("src/main/css")
    into("build/js/packages/kustom/css")
}

tasks["processResources"].dependsOn(tasks["sass"])