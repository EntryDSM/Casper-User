import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(Plugin.KOTLIN_JVM) version PluginVersion.KOTLIN_VERSION
    id(Plugin.KOTLIN_SPRING) version PluginVersion.KOTLIN_VERSION
    id(Plugin.KOTLIN_JPA) version PluginVersion.KOTLIN_VERSION
    id(Plugin.KOTLIN_KAPT)
    id(Plugin.SPRING_BOOT) version PluginVersion.SPRING_BOOT_VERSION
    id(Plugin.SPRING_DEPENDENCY_MANAGEMENT) version PluginVersion.SPRING_DEPENDENCY_MANAGEMENT_VERSION
    id(Plugin.CASPER_DOCUMENTATION)
    id(Plugin.PROTOBUF) version PluginVersion.PROTOBUF_VERSION
    id(Plugin.OSDETECTOR) version PluginVersion.OSDETECTOR_VERSION
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${DependencyVersion.SPRING_CLOUD}")
    }
}

dependencies {
    // 스프링 부트 기본 기능
    implementation(Dependencies.SPRING_BOOT_STARTER)

    // 코틀린 리플렉션
    implementation(Dependencies.KOTLIN_REFLECT)

    // 스프링 부트 테스트 도구
    testImplementation(Dependencies.SPRING_BOOT_STARTER_TEST)

    // 코틀린 + JUnit5 테스트
    testImplementation(Dependencies.KOTLIN_TEST_JUNIT5)

    // JUnit5 실행 런처
    testRuntimeOnly(Dependencies.JUNIT_PLATFORM_LAUNCHER)

    // 웹 관련
    implementation(Dependencies.SPRING_BOOT_STARTER_WEB)

    // 데이터베이스
    implementation(Dependencies.SPRING_BOOT_STARTER_DATA_JPA)
    implementation(Dependencies.SPRING_BOOT_STARTER_DATA_REDIS)
    runtimeOnly(Dependencies.MYSQL_CONNECTOR)

    // 보안
    implementation(Dependencies.SPRING_BOOT_STARTER_SECURITY)

    // 검증
    implementation(Dependencies.SPRING_BOOT_STARTER_VALIDATION)

    // JSON 처리
    implementation(Dependencies.JACKSON_MODULE_KOTLIN)
    implementation(Dependencies.ORG_JSON)

    // JWT
    implementation(Dependencies.JWT_API)
    implementation(Dependencies.JWT_IMPL)
    runtimeOnly(Dependencies.JWT_JACKSON)

    implementation(Dependencies.MAPSTRUCT)
    kapt(Dependencies.MAPSTRUCT_PROCESSOR)

    // grpc
    implementation(Dependencies.GRPC_NETTY_SHADED)
    implementation(Dependencies.GRPC_PROTOBUF)
    implementation(Dependencies.GRPC_STUB)
    implementation(Dependencies.GRPC_KOTLIN_STUB)
    implementation(Dependencies.PROTOBUF_KOTLIN)
    implementation(Dependencies.GRPC_SERVER_SPRING_BOOT_STARTER)
    testImplementation(Dependencies.GRPC_TESTING)

    // OkCert
    implementation(files("$projectDir/${Dependencies.OKCERT_PATH}"))

    // swagger
    implementation(Dependencies.SWAGGER)

    // Sentry
    implementation(Dependencies.SENTRY_SPRING_BOOT_STARTER)

    //kafka
    implementation(Dependencies.KAFKA)

    // Spring Cloud Config
    implementation(Dependencies.SPRING_CLOUD_STARTER_CONFIG)
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${DependencyVersion.PROTOBUF}"
    }
    plugins {
        create("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:${DependencyVersion.GRPC}:${osdetector.classifier}"
        }
        create("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:${DependencyVersion.GRPC_KOTLIN}:jdk8@jar"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                create("grpc")
                create("grpckt")
            }
        }
    }
}


repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(17)
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
