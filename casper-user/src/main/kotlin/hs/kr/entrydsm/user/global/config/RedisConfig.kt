package hs.kr.entrydsm.user.global.config

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

/**
 * Redis 설정 클래스입니다.
 *
 * 이 클래스는 애플리케이션에서 Redis를 사용할 때 필요한
 * 연결 팩토리 및 직렬화/역직렬화 설정을 담당합니다.
 *
 * - `redisConnectionFactory`: host와 port를 기반으로 Redis 연결을 생성합니다.
 * - `redisTemplate`: 문자열 기반 key와 JSON 직렬화를 사용하는 RedisTemplate을 구성합니다.
 * - `objectMapper`: Kotlin 모듈 및 기본 타입 정보를 포함한 Jackson ObjectMapper를 제공합니다.
 *
 * @property redisHost Redis 서버의 호스트명
 * @property redisPort Redis 서버의 포트
 */
@Configuration
class RedisConfig(
    @Value("\${spring.redis.host}")
    private val redisHost: String,
    @Value("\${spring.redis.port}")
    private val redisPort: Int,
) {
    /**
     * Redis 연결 팩토리를 명시적으로 설정합니다.
     *
     * @return RedisConnectionFactory (Lettuce 기반)
     */
    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        return LettuceConnectionFactory(redisHost, redisPort)
    }

    /**
     * RedisTemplate을 설정합니다.
     *
     * - Key는 문자열(StringRedisSerializer)로 직렬화합니다.
     * - Value는 Jackson2JsonRedisSerializer를 사용하여 JSON 직렬화합니다.
     *
     * @param connectionFactory Redis 연결 팩토리
     * @param objectMapper JSON 직렬화를 위한 ObjectMapper
     * @return 구성된 RedisTemplate
     */
    @Bean
    fun redisTemplate(
        connectionFactory: RedisConnectionFactory,
        objectMapper: ObjectMapper,
    ): RedisTemplate<String, Any> {
        val template = RedisTemplate<String, Any>()
        template.connectionFactory = connectionFactory
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = Jackson2JsonRedisSerializer(objectMapper, Any::class.java)
        return template
    }

    /**
     * JSON 직렬화를 위한 ObjectMapper를 설정합니다.
     *
     * - KotlinModule을 등록하여 Kotlin 클래스 직렬화를 지원합니다.
     * - 기본 타입 정보 포함 설정(DefaultTyping.NON_FINAL)을 활성화합니다.
     * - 알 수 없는 프로퍼티 무시 설정을 적용합니다.
     *
     * @return 구성된 ObjectMapper
     */
    @Bean
    fun objectMapper(): ObjectMapper {
        val mapper = ObjectMapper()
        mapper.registerModule(KotlinModule.Builder().build())
        mapper.activateDefaultTyping(
            LaissezFaireSubTypeValidator(),
            ObjectMapper.DefaultTyping.NON_FINAL,
            JsonTypeInfo.As.PROPERTY,
        )
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        return mapper
    }
}
