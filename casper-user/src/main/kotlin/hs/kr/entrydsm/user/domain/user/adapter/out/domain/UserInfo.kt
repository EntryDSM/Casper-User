package hs.kr.entrydsm.user.domain.user.adapter.out.domain

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import org.springframework.data.redis.core.index.Indexed

/**
 * Redis에 저장되는 사용자 인증 정보를 나타내는 클래스입니다.
 * JWT 토큰과 관련된 사용자 정보를 캐시하여 인증 성능을 향상시킵니다.
 *
 * @property token JWT 토큰 (Redis 키로 사용)
 * @property userId 사용자 식별자
 * @property userRole 사용자 역할
 * @property ttl Time To Live (토큰 만료 시간, 초 단위)
 */
@RedisHash
class UserInfo(
    @Id
    val token: String,
    @Indexed
    val userId: String,
    @Indexed
    val userRole: String,
    @TimeToLive
    val ttl: Long,
)
