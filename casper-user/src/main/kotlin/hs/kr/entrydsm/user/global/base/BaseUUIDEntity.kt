package hs.kr.entrydsm.user.global.base

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import java.util.UUID

/**
 * UUID 기본키를 사용하는 JPA 엔티티의 기본 클래스입니다.
 * 모든 엔티티가 공통으로 사용하는 UUID 기본키 필드를 정의합니다.
 *
 * @property id UUID 타입의 기본키
 */
@MappedSuperclass
abstract class BaseUUIDEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(
        columnDefinition = "BINARY(16)",
        nullable = false,
    )
    val id: UUID?,
)
