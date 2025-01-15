
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val exposedVersion: String by project
val mysqlVersion: String by project
val hikariCpVersion: String by project
val koinVersion: String by project

plugins {
	kotlin("jvm") version "1.9.23"
	kotlin("plugin.spring") version "1.9.23"
	kotlin("plugin.jpa") version "1.9.23"
	id("io.ktor.plugin") version "2.3.2"
	id("org.jetbrains.kotlin.plugin.serialization") version "1.9.23"
	id("org.jlleitschuh.gradle.ktlint") version "11.5.0"
	id("com.google.cloud.tools.jib") version "3.3.1"

}




repositories {
	mavenCentral()
	maven {
		url = uri("https://plugins.gradle.org/m2/")
	}

}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "21"
	}
}


tasks.withType<Jar> {
	manifest {
		attributes["Main-Class"] = "com.ld.server.ServerApplicationKt"
	}

}



application {
	mainClass.set("com.ld.server.SeverApplicationKt")
}

//kotlin {
//	jvmToolchain {
//		val javaVersion = JavaVersion.VERSION_17.toString()
//		languageVersion.set(JavaLanguageVersion.of(javaVersion))
//	}
//}

// or alternatively
kotlin {
	jvmToolchain {
		languageVersion.set(JavaLanguageVersion.of(21))
	}
}



tasks {
	named<ShadowJar>("shadowJar") {
		manifest {
			attributes(
				"Main-Class" to "com.ld.server.ServerApplicationKt",
				"Multi-Release" to true
			)
		}
	}
}


// After Kotlin 1.7.20
////kotlin {
	//jvmToolchain(21)
//}
//kotlin {
//    jvmToolchain(18)
//}

group = "com.ld.server"
version = "0.0.1"
application {
	mainClass.set("com.ld.server.ServerApplicationKt")

	val isDevelopment: Boolean = project.ext.has("development")
	applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
	google()
	mavenCentral()
	gradlePluginPortal()
}



dependencies {
	implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
	implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")
	implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
	implementation("io.ktor:ktor-server-config-yaml:$ktorVersion")
	implementation("io.ktor:ktor-serialization-jackson-jvm:$ktorVersion")
	implementation("io.ktor:ktor-server-call-logging-jvm:$ktorVersion")
	implementation("io.ktor:ktor-server-swagger-jvm:$ktorVersion")
	implementation("io.github.smiley4:ktor-swagger-ui:3.5.0")

	implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
	implementation("io.ktor:ktor-server-websockets-jvm:2.3.2")
    implementation("com.google.firebase:firebase-firestore-ktx:25.1.1")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
	implementation("io.ktor:ktor-server-status-pages:$kotlinVersion")

	implementation("mysql:mysql-connector-java:$mysqlVersion")

	implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
	implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
	implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
	implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")
	implementation("ch.qos.logback:logback-classic:$logbackVersion")
	implementation("com.zaxxer:HikariCP:$hikariCpVersion")

	implementation("io.insert-koin:koin-ktor:$koinVersion")
	implementation("io.insert-koin:koin-logger-slf4j:$koinVersion")

	implementation("io.ktor:ktor-server-websockets:$ktorVersion")

	// jwt
	implementation("io.ktor:ktor-server-auth:$ktorVersion")
	implementation("io.ktor:ktor-server-auth-jwt:$ktorVersion")
	implementation("io.ktor:ktor-client-core:$ktorVersion")
	implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")

	implementation("io.ktor:ktor-client-core:$ktorVersion")
	implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
	implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
	implementation("io.ktor:ktor-client-android:$ktorVersion")
	implementation("io.ktor:ktor-client-logging:$ktorVersion")


	implementation("com.google.cloud.tools:jib-gradle-plugin:3.3.1")


	implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.4")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
	implementation("io.ktor:ktor-client-core:2.3.4")
	implementation("io.ktor:ktor-client-cio:2.3.4")
	implementation("io.ktor:ktor-client-content-negotiation:2.3.4")
	implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.4")
	implementation("io.ktor:ktor-client-core:2.3.4")
	implementation("io.ktor:ktor-client-cio:2.3.4")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
	implementation("io.ktor:ktor-server-auth-jwt:2.3.4")
//	implementation("androidx.lifecycl:lifecycle-runtime-ktx:2.3.1")
//	implementation("androidx.core:1.15.0")
	// Retrofit
	implementation("com.squareup.retrofit2:retrofit:2.9.0")
	implementation("com.squareup.retrofit2:converter-gson:2.9.0")



}



