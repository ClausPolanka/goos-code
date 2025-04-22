// build.gradle.kts
plugins {
    `java`
}

group = "auctionsniper"           // passe das Group‑Id gerne an
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
    // für eventuelle SNAPSHOT‑Abhängigkeiten
    maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
    // lokaler Fallback für alle JARs, die nicht in Maven Central liegen
    flatDir {
        dirs("lib/deploy", "lib/develop")
    }
}

dependencies {
    // → application (compile) dependencies
    implementation("commons-io:commons-io:1.4")
    implementation("commons-lang:commons-lang:2.4")
    implementation("org.igniterealtime.smack:smack:3.1.0")
    implementation("org.igniterealtime.smackx:smackx:3.1.0")
    implementation("cglib:cglib-nodep:2.2")
    implementation("org.objenesis:objenesis:1.0")
    // falls doch noch lokal:
    implementation(fileTree("lib/deploy") {
        include("*.jar")
    })

    // → test dependencies
    testImplementation("junit:junit:4.6")
    testImplementation("org.hamcrest:hamcrest-core:1.2")
    testImplementation("org.hamcrest:hamcrest-library:1.2")
    testImplementation("org.jmock:jmock:2.6.0") // falls in Maven, sonst siehe Fallback
    // Fallback für lokale SNAPSHOTs, cglib‑Sourcen, windowlicker‑JARs etc.
    testImplementation(fileTree("lib/develop") {
        include("*.jar")
        exclude("*-src.jar")
    })
}

tasks.withType<Test> {
    // ausführliche Test‑Logs
    testLogging {
        events("passed", "skipped", "failed")
    }
}
