package hs.kr.entrydsm.user.infrastructure.kafka.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding

/**
 * Kafka 연결을 위한 설정 프로퍼티 클래스입니다.
 * 
 * application.yml의 kafka 섹션에서 설정값을 바인딩하여
 * Confluent Cloud Kafka 클러스터 연결에 필요한 정보를 관리합니다.
 * 
 * @property serverAddress Kafka 브로커 서버 주소
 * @property confluentApiKey Confluent Cloud 접근을 위한 API 키
 * @property confluentApiSecret Confluent Cloud 접근을 위한 API 시크릿
 */
@ConfigurationPropertiesBinding
@ConfigurationProperties("kafka")
class KafkaProperty(
    val serverAddress: String,
    val confluentApiKey: String,
    val confluentApiSecret: String
)
