package hs.kr.entrydsm.user.global.config

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Configuration

/**
 * Configuration Properties 스캔 설정 클래스입니다.
 * 애플리케이션 전반의 설정 프로퍼티들을 자동으로 스캔하여 등록합니다.
 */
@Configuration
@ConfigurationPropertiesScan("hs.kr.entrydsm")
class ConfigurationProperties
