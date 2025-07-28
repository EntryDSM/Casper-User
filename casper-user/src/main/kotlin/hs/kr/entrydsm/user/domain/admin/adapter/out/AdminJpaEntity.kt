package hs.kr.entrydsm.user.domain.admin.adapter.out

import hs.kr.entrydsm.user.global.base.BaseUUIDEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import java.util.UUID

/**
 * 관리자 정보를 데이터베이스에 저장하기 위한 JPA 엔티티 클래스입니다.
 * 데이터베이스의 tbl_admin 테이블과 매핑됩니다.
 *
 * @property adminId 관리자 로그인 ID
 * @property password 해시화된 비밀번호
 */
@Entity(name = "tbl_admin")
class AdminJpaEntity(
    id: UUID?,
    @Column(name = "admin_id", length = 15, nullable = false)
    val adminId: String,
    @Column(name = "password", length = 60, nullable = false)
    val password: String,
) : BaseUUIDEntity(id)
