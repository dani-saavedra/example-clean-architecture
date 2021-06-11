import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.wix.mysql.*
import com.wix.mysql.EmbeddedMysql.anEmbeddedMysql
import com.wix.mysql.config.MysqldConfig.aMysqldConfig
import com.wix.mysql.distribution.Version.v8_0_17


val unitTestPath = "unit-test"
val dbUser = "myUserClean"
val dbPassword = "myPassClean"
val dbName = "myDbClean"
val dbVersion = v8_0_17


group = "com.danisaavedra."
version = "0.0.1-SNAPSHOT"

buildscript {
    dependencies {
        classpath("com.wix:wix-embedded-mysql:4.6.1")
    }
}

repositories {
    mavenLocal()
    jcenter()
    mavenCentral()

}

plugins {
    val kotlinVersion = "1.4.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    id("org.springframework.boot") version "2.1.13.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    id("io.gitlab.arturbosch.detekt") version "1.6.0"
    id("com.github.johnrengelman.processes") version "0.5.0"
    id("org.springdoc.openapi-gradle-plugin") version "1.2.0"
    id("com.expediagroup.graphql") version "4.0.0-alpha.12"
    jacoco
    idea
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_11
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

    withType<Test> {
        useJUnitPlatform()
    }

    idea {
        module {
            val testDirs = listOf(unitTestPath).map { file("src/$it/kotlin") }
            testSourceDirs.addAll(testDirs)
            testResourceDirs.addAll(testDirs)
        }
    }

    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "1.8"
        }
    }

    jacoco {
        toolVersion = "0.8.6"
    }

    fun getJacocoExecutionFiles(buildDir: File): Array<File> {
        val testsTasks = listOf("unitTest")
        val taskNames = project.gradle.startParameter.taskNames
        return taskNames
            .filter { testsTasks.contains(it) }
            .toMutableSet()
            .plus(if (taskNames.contains("test")) setOf("unitTest") else emptySet())
            .map { File(buildDir, "/jacoco/$it.exec") }
            .toTypedArray()
    }

    val jacocoRootReport by creating(JacocoReport::class) {
        group = "verification"
        sourceDirectories.setFrom(files(sourceSets["main"].allSource.srcDirs))
        classDirectories.setFrom(files(sourceSets["main"].output))
        val jacocoExecutionFiles = getJacocoExecutionFiles(buildDir)
        logger.info("jacocoExecutionFiles: ${jacocoExecutionFiles.map { it.name }}")
        executionData(*jacocoExecutionFiles)
        reports {
            val reportPath = "${buildDir}/reports/jacoco/test"
            html.isEnabled = true
            html.destination = file("$reportPath/html")
            xml.isEnabled = true
            xml.destination = file("$reportPath/jacocoTestReport.xml")
        }
        afterEvaluate {
            val excluded = listOf(
                "**/Application**",
                "**/model/**",
                "**/dto/**",
                "**/orm/**",
                "**/config/**",
                "**/telemetry/**",
                "**/response/**"
            )
            val classDirectoriesWithExcluded = files(classDirectories.files.map { fileTree(it).exclude(excluded) })
            classDirectories.setFrom(classDirectoriesWithExcluded)
        }
    }

    bootRun {
        this.environment("APPLICATION", rootProject.name)
    }

    bootJar {
        archiveFileName.set("application.jar")
    }

    val unitTest = task<Test>("unitTest") {
        description = "Runs the unit tests"
        group = "verification"
        testClassesDirs = sourceSets[unitTestPath].output.classesDirs
        classpath = sourceSets[unitTestPath].runtimeClasspath
        finalizedBy(jacocoRootReport)
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
        finalizedBy(jacocoRootReport)
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
        exclude("org.mockito", "mockito-core")
    }
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.2")
    testImplementation("com.ninja-squad:springmockk:1.1.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.2")
}
