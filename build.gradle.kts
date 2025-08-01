plugins {
    id(Plugin.KOTLIN_JVM) version PluginVersion.KOTLIN_VERSION apply false
    id(Plugin.DETEKT) version PluginVersion.DETEKT_VERSION
    id(Plugin.KTLINT) version PluginVersion.KTLINT_VERSION apply false
}

subprojects {
    group = "hs.kr.entrydsm"
    version = "0.0.1"

    apply(plugin = Plugin.KTLINT)

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        filter {
            exclude("**/build/generated/**")
            exclude("**/*Grpc*.kt")
            exclude("**/*Proto*.kt")
        }
    }
}

tasks.register("checkAll") {
    group = "verification"
    description = "모든 모듈(includeBuild 포함)에 대해 check 태스크를 실행합니다"

    subprojects.forEach { subproject ->
        dependsOn(subproject.tasks.matching { it.name.startsWith("check") })
    }

    dependsOn(gradle.includedBuilds.map { it.task(":check") })
}

detekt {
    config.setFrom(files("detekt.yml"))
    buildUponDefaultConfig = false
    parallel = true
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    reports {
        xml.required.set(false)
        txt.required.set(false)
    }

    jvmTarget = "17"
}