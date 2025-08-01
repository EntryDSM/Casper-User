package hs.kr.entrydsm.user.domain.user.adapter.out.domain

import hs.kr.entrydsm.user.domain.user.model.User
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import java.util.UUID

/**
 * Redis에 저장되는 사용자 캐시 데이터를 나타내는 클래스입니다.
 * 사용자 조회 성능 향상을 위한 캐시 데이터를 관리합니다.
 *
 * @property id 사용자 고유 식별자 (Redis 키로 사용)
 * @property phoneNumber 전화번호
 * @property name 사용자 이름
 * @property isParent 학부모 여부
 * @property receiptCode 지원서 접수번호
 * @property role 사용자 역할
 * @property ttl Time To Live (캐시 만료 시간, 초 단위)
 */
@RedisHash(value = "user_cache")
data class UserCache(
    @Id
    val id: UUID?,
    val phoneNumber: String,
    val name: String,
    val isParent: Boolean,
    val receiptCode: Long?,
    val role: UserRole,
    @TimeToLive
    val ttl: Long,
) {
    companion object {
        /**
         * User 도메인 모델로부터 UserCache 인스턴스를 생성합니다.
         *
         * @param user 도메인 모델 User 인스턴스
         * @return UserCache 인스턴스 (TTL 10분으로 설정)
         */
        fun from(user: User): UserCache {
            return UserCache(
                id = user.id,
                phoneNumber = user.phoneNumber,
                name = user.name,
                isParent = user.isParent,
                receiptCode = user.receiptCode,
                role = user.role,
                ttl = 60 * 10,
            )
        }
    }
}
