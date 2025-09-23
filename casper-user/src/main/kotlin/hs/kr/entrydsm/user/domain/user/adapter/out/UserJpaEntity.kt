package hs.kr.entrydsm.user.domain.user.adapter.out

import hs.kr.entrydsm.user.domain.user.adapter.out.domain.UserRole
import hs.kr.entrydsm.user.global.base.BaseUUIDEntity
import hs.kr.entrydsm.user.global.converter.EncryptedStringConverter
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.time.LocalDateTime
import java.util.UUID

/**
 * 사용자 정보를 데이터베이스에 저장하기 위한 JPA 엔티티 클래스입니다.
 * 데이터베이스의 tbl_user 테이블과 매핑되며, 민감한 정보는 암호화하여 저장합니다.
 *
 * @property phoneNumber 암호화된 전화번호
 * @property phoneNumberHash 조회를 위한 해시화된 전화번호
 * @property password 해시화된 비밀번호
 * @property name 암호화된 사용자 이름
 * @property isParent 학부모 여부
 * @property receiptCode 지원서 접수번호
 * @property role 사용자 역할
 * @property isActive 계정 활성화 상태
 * @property withdrawalAt 탈퇴 일시
 */
@Entity(name = "tbl_user")
class UserJpaEntity(
    id: UUID?,
    @Column(columnDefinition = "varchar(255)", nullable = false, unique = true)
    @Convert(converter = EncryptedStringConverter::class)
    val phoneNumber: String,
    @Column(name = "phone_number_hash", columnDefinition = "varchar(255)", nullable = false, unique = true)
    val phoneNumberHash: String,
    @Column(columnDefinition = "varchar(255)", nullable = false)
    var password: String,
    @Column(columnDefinition = "varchar(255)", nullable = false)
    @Convert(converter = EncryptedStringConverter::class)
    val name: String,
    @Column(columnDefinition = "bit(1) default 1", nullable = false)
    val isParent: Boolean,
    @Column(name = "receipt_code", nullable = true)
    var receiptCode: Long?,
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    val role: UserRole,
    @Column(name = "active", columnDefinition = "tinyint(1) default 1", nullable = false)
    var active: Boolean = true,
    @Column(name = "withdrawal_at", nullable = true)
    var withdrawalAt: LocalDateTime? = null,
) : BaseUUIDEntity(id)
