plugins {
    java
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.hibernate.orm") version "6.3.1.Final"
    id("org.graalvm.buildtools.native") version "0.9.28"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
}

group = "heig.bdr"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

extra["snippetsDir"] = file("build/generated-snippets")

dependencies {
    // dev utils
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    compileOnly("org.projectlombok:lombok")
    testCompileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")

    // database
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.hibernate.orm:hibernate-spatial")
    testImplementation("org.hibernate.orm:hibernate-ant")
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.liquibase:liquibase-core")

    // geospatial data
    implementation("org.n52.jackson:jackson-datatype-jts:1.2.10")
    implementation("org.locationtech.jts:jts-core:1.19.0")

    // API
    // implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")

    // ViewModel Mapping
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    implementation("org.mapstruct.extensions.spring:mapstruct-spring-annotations:1.1.1")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
    annotationProcessor("org.mapstruct.extensions.spring:mapstruct-spring-extensions:1.1.1")

    // OpenAPI
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

    // Batches
    implementation("org.springframework.boot:spring-boot-starter-batch")

    // Security
    implementation("org.bouncycastle:bcprov-jdk18on:1.77")
    implementation("io.jsonwebtoken:jjwt-api:0.12.3")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.3")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.3")

    // testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("org.springframework.security:spring-security-test")
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }

    test {
        project.property("snippetsDir")!!.let { outputs.dir(it) }
    }

    asciidoctor {
        project.property("snippetsDir")!!.let { inputs.dir(it) }
        dependsOn(test)
    }

    compileJava {
        options.compilerArgs.addAll(listOf(
                "-Amapstruct.verbose=true",
                "-Amapstruct.defaultComponentModel=spring"
        ))
    }
}

hibernate {
    enhancement {
        enableAssociationManagement.set(true)
    }
}
