package hs.kr.entrydsm.user.infrastructure.kafka.configuration

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.serializer.JsonSerializer

/**
 * Kafka Producer 설정을 담당하는 Configuration 클래스입니다.
 *
 * 사용자 삭제, 전체 테이블 삭제, 사용자 이벤트 발행을 위한
 * 각각의 KafkaTemplate과 ProducerFactory를 구성하며,
 * Confluent Cloud 연결을 위한 보안 설정을 포함합니다.
 *
 * @property kafkaProperty Kafka 연결 정보를 담은 프로퍼티
 */
@Configuration
class KafkaProducerConfig(
    private val kafkaProperty: KafkaProperty,
) {
    /**
     * 전체 테이블 삭제 이벤트용 Producer Factory를 생성합니다.
     *
     * @return Unit 타입 메시지용 DefaultKafkaProducerFactory
     */
    @Bean
    fun deleteAllTableProducerFactory(): DefaultKafkaProducerFactory<String, Unit> {
        return DefaultKafkaProducerFactory(producerConfig())
    }

    /**
     * 전체 테이블 삭제 이벤트 발행을 위한 KafkaTemplate을 생성합니다.
     *
     * @return Unit 타입 메시지용 KafkaTemplate
     */
    @Bean
    fun deleteAllTableKafkaTemplate(): KafkaTemplate<String, Unit> {
        return KafkaTemplate(deleteAllTableProducerFactory())
    }

    /**
     * 사용자 삭제 이벤트용 Producer Factory를 생성합니다.
     *
     * @return Long 타입 메시지용 DefaultKafkaProducerFactory
     */
    @Bean
    fun deleteUserProducerFactory(): DefaultKafkaProducerFactory<String, Long> {
        return DefaultKafkaProducerFactory(producerConfig())
    }

    /**
     * 사용자 삭제 이벤트 발행을 위한 KafkaTemplate을 생성합니다.
     *
     * @return Long 타입 메시지용 KafkaTemplate
     */
    @Bean
    fun deleteUserKafkaTemplate(): KafkaTemplate<String, Long> {
        return KafkaTemplate(deleteUserProducerFactory())
    }

    /**
     * 사용자 이벤트용 Producer Factory를 생성합니다.
     *
     * @return Any 타입 메시지용 DefaultKafkaProducerFactory
     */
    @Bean
    fun userEventProducerFactory(): DefaultKafkaProducerFactory<String, Any> {
        return DefaultKafkaProducerFactory(producerConfig())
    }

    /**
     * 사용자 이벤트 발행을 위한 KafkaTemplate을 생성합니다.
     *
     * @return Any 타입 메시지용 KafkaTemplate
     */
    @Bean
    fun userEventKafkaTemplate(): KafkaTemplate<String, Any> {
        return KafkaTemplate(userEventProducerFactory())
    }

    /**
     * Kafka Producer의 기본 설정을 구성합니다.
     *
     * Confluent Cloud 연결을 위한 SASL 보안 설정과 직렬화 설정을 포함합니다.
     *
     * @return Producer 설정 맵
     */
    private fun producerConfig(): Map<String, Any> {
        return mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaProperty.serverAddress,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java,
            "security.protocol" to "SASL_PLAINTEXT",
            "sasl.mechanism" to "SCRAM-SHA-512",
            "sasl.jaas.config" to
                "org.apache.kafka.common.security.scram.ScramLoginModule required " +
                "username=\"${kafkaProperty.confluentApiKey}\" " +
                "password=\"${kafkaProperty.confluentApiSecret}\";",
        )
    }
}
