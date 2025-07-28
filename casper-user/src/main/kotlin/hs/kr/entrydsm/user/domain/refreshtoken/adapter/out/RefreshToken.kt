package hs.kr.entrydsm.user.domain.refreshtoken.adapter.out

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import org.springframework.data.redis.core.index.Indexed

/**
 * Redis에 저장되는 리프레시 토큰 정보를 나타내는 클래스입니다.
 * JWT 리프레시 토큰을 관리하여 토큰 갱신을 처리합니다.
 *
 * @property id 사용자 ID (Redis 키로 사용)
 * @property token 리프레시 토큰 값
 * @property ttl Time To Live (토큰 만료 시간, 초 단위)
 */
@RedisHash
class RefreshToken(
    @Id
    val id: String,
    @Indexed
    var token: String,
    @TimeToLive
    var ttl: Long,
) {
    /**
     * 리프레시 토큰과 TTL을 업데이트합니다.
     *
     * @param token 새로운 리프레시 토큰
     * @param ttl 새로운 만료 시간 (초)
     */
    fun update(
        token: String?,
        ttl: Long,
    ) {
        this.token = token!!
        this.ttl = ttl
    }
}
