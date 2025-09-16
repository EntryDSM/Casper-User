package hs.kr.entrydsm.user.domain.user.adapter.out.persistence.repository

import hs.kr.entrydsm.user.domain.user.adapter.out.UserJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime
import java.util.UUID

/**
 * 사용자 JPA 엔티티를 위한 저장소 인터페이스입니다.
 * Spring Data JPA를 통해 기본 CRUD 작업과 커스텀 쿼리를 제공합니다.
 */
interface UserRepository : JpaRepository<UserJpaEntity, UUID> {
    /**
     * 전화번호로 사용자를 조회합니다.
     *
     * @param phoneNumberHash 조회할 전화번호
     * @return 조회된 사용자 엔티티 (없으면 null)
     */
    fun findByPhoneNumberHash(phoneNumberHash: String): UserJpaEntity?

    /**
     * 전화번호로 사용자 존재 여부를 확인합니다.
     *
     * @param phoneNumberHash 확인할 전화번호
     * @return 사용자 존재 여부
     */
    fun existsByPhoneNumberHash(phoneNumberHash: String): Boolean

    /**
     * ID로 사용자를 삭제합니다.
     *
     * @param id 삭제할 사용자 ID
     */
    fun deleteById(id: UUID?)

    /**
     * 지정된 일시 이전에 탈퇴한 비활성 사용자들을 조회합니다.
     *
     * @param cutoffDate 기준 일시
     * @return 삭제 대상 사용자 엔티티 목록
     */
    fun findAllByIsActiveFalseAndWithdrawalAtBefore(cutoffDate: LocalDateTime): List<UserJpaEntity>
}
