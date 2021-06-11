import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.wix.mysql.*
import com.wix.mysql.EmbeddedMysql.anEmbeddedMysql
import com.wix.mysql.config.MysqldConfig.aMysqldConfig
import com.wix.mysql.distribution.Version.v8_0_17

group = "com.cleanarchitecture"
version = "1.0.0"
val unitTestPath = "unit-test"
val dbUser = "myUserClean"
val dbPassword = "myPassClean"
val dbName = "myDbClean"
val dbVersion = v8_0_17

buildscript {
    dependencies {
        classpath("com.wix:wix-embedded-mysql:4.6.1")
    }
}

plugins {
    val kotlinVersion = "1.4.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    id("org.springframework.boot") version "2.3.11.RELEASE"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    jacoco
    idea
}

group = "com.danisaavedra."
version = "0.0.1-SNAPSHOT"
java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_11
}
repositories {
    mavenCentral()
}

sourceSets {
    fun createSourceTest(testSourcePath: String) {
        create(testSourcePath) {
            kotlin { file("$projectDir/src/$testSourcePath/kotlin") }
            resources.srcDir(file("$projectDir/src/$testSourcePath/resources"))
            compileClasspath += sourceSets.getByName("main").output + configurations.getByName("testRuntimeClasspath")
            runtimeClasspath += output + compileClasspath
        }
    }
    createSourceTest(unitTestPath)
}

configurations.all {
    exclude("junit", "junit")
}

tasks {
    val unitTest = task<Test>("unitTest") {
        description = "Runs the unit tests"
        group = "verification"
        testClassesDirs = sourceSets[unitTestPath].output.classesDirs
        classpath = sourceSets[unitTestPath].runtimeClasspath
        var mysqlServer: EmbeddedMysql? = null
        doFirst {
            logger.info("Starting embedded mysql")
            val mysqlConfig = aMysqldConfig(dbVersion).withFreePort().withUser(dbUser, dbPassword).build()
            systemProperty("datasource.port", mysqlConfig.port)
            mysqlServer = anEmbeddedMysql(mysqlConfig).addSchema(dbName).start()
            logger.info("Embedded mysql started")
        }
        doLast {
            mysqlServer?.run {
                stop()
                logger.info("Embedded mysql stopped")
            }
        }
        this.environment("APPLICATION", rootProject.name)
    }

    test {
        description = "Runs all tests"
        dependsOn(unitTest)
    }


}
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.beust:klaxon:5.5")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.flywaydb:flyway-core")
    runtimeOnly("mysql:mysql-connector-java")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
