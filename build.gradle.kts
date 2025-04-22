plugins {
    `java`
}

group = "auctionsniper"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
    maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
    // nur für JARs, die wirklich nicht in Central/Sonatype liegen:
    flatDir {
        dirs("lib/deploy", "lib/develop")
    }
}

dependencies {
    // → wirklich remote auflösbare Bibliotheken
    implementation("commons-io:commons-io:1.4")
    implementation("commons-lang:commons-lang:2.4")
    testImplementation("junit:junit:4.6")
    testImplementation("org.hamcrest:hamcrest-core:1.2")
    testImplementation("org.hamcrest:hamcrest-library:1.2")
    testImplementation("org.jmock:jmock:2.6.0")

    // → alles, was nicht in Maven Central liegt, aus Deinem lib-Ordner:
    implementation(fileTree("lib/deploy") {
        include("*.jar")
        // bei Bedarf bestimmte JARs ausschließen, z.B. Sources
        // exclude("*-src.jar")
    })
    testImplementation(fileTree("lib/develop") {
        include("*.jar")
        exclude("*-src.jar")
    })
}

tasks.withType<Test> {
    testLogging {
        events("passed", "skipped", "failed")
    }
}
