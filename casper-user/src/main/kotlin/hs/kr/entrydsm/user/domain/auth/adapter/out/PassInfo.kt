package hs.kr.entrydsm.user.domain.auth.adapter.out

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import org.springframework.data.redis.core.index.Indexed

/**
 * Redis에 저장되는 Pass 인증 정보를 나타내는 클래스입니다.
 * Pass 인증을 통해 검증된 사용자 정보를 임시로 저장합니다.
 *
 * @property phoneNumber 암호화된 전화번호 (Redis 키로 사용)
 * @property name 암호화된 사용자 이름
 * @property ttl Time To Live (데이터 만료 시간, 초 단위)
 */
@RedisHash
class PassInfo(
    @Id
    val phoneNumber: String,
    @Indexed
    val name: String,
    @TimeToLive
    val ttl: Long
)
