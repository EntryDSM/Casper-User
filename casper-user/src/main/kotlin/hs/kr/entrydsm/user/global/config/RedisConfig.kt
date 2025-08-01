package hs.kr.entrydsm.user.global.config

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

/**
 * Redis 설정 클래스입니다.
 * Redis 연결 및 직렬화 설정을 담당합니다.
 */
@Configuration
class RedisConfig {
    /**
     * Redis Template을 설정합니다.
     * 문자열 키와 JSON 직렬화를 사용하는 Redis 템플릿을 구성합니다.
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
     * Kotlin 모듈과 타입 정보를 포함한 JSON 직렬화를 지원합니다.
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
