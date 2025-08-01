package hs.kr.entrydsm.user.domain.admin.adapter.out.persistence.repository

import hs.kr.entrydsm.user.domain.admin.adapter.out.AdminJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

/**
 * 관리자 JPA 엔티티에 대한 데이터 액세스를 담당하는 리포지토리 인터페이스입니다.
 */
interface AdminRepository : JpaRepository<AdminJpaEntity, UUID> {
    /**
     * 관리자 ID로 관리자를 조회합니다.
     */
    fun findByAdminId(adminId: String): AdminJpaEntity?
}
