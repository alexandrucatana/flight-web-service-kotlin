import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("plugin.jpa") version "1.2.71"
	id("org.springframework.boot") version "2.1.6.RELEASE"
	id("io.spring.dependency-management") version "1.0.7.RELEASE"
	kotlin("jvm") version "1.2.71"
	kotlin("plugin.spring") version "1.2.71"
}

group = "com.org"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

val developmentOnly by configurations.creating
configurations {
	runtimeClasspath {
		extendsFrom(developmentOnly)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation ("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
	implementation ("org.springframework.boot:spring-boot-starter-webflux")
	implementation ("org.springframework.boot:spring-boot-starter-actuator")

	compile ("net.logstash.logback:logstash-logback-encoder:5.3")
	compile ("ch.qos.logback:logback-classic:1.2.3")

	implementation ("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation ("org.jetbrains.kotlin:kotlin-reflect")
	implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation ("de.flapdoodle.embed:de.flapdoodle.embed.mongo")

	testImplementation ("org.junit.jupiter:junit-jupiter-api:5.3.1")
	testImplementation ("org.junit.jupiter:junit-jupiter-engine:5.3.1")
	testImplementation ("de.flapdoodle.embed:de.flapdoodle.embed.mongo")
	testImplementation ("com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0")
	testImplementation ("io.projectreactor:reactor-test")
	testImplementation ("org.springframework.restdocs:spring-restdocs-webtestclient")

	testCompile ("org.springframework.cloud:spring-cloud-contract-wiremock:2.1.0.RELEASE")
	testRuntime ("org.junit.jupiter:junit-jupiter-engine:5.3.1")


}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}
