package hs.kr.entrydsm.user.global.base

import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

/**
 * 생성 및 수정 시간을 자동으로 관리하는 JPA 엔티티의 기본 클래스입니다.
 * Spring Data JPA의 Auditing 기능을 사용하여 엔티티의 생성/수정 시간을 자동으로 설정합니다.
 *
 * @property createdAt 엔티티 생성 시간
 * @property modifiedAt 엔티티 최종 수정 시간
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseTimeEntity(
    @CreatedDate
    private val createdAt: LocalDateTime? = LocalDateTime.now(),
    @LastModifiedDate
    private val modifiedAt: LocalDateTime? = LocalDateTime.now(),
)
