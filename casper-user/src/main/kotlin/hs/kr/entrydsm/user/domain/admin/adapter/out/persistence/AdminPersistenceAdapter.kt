package hs.kr.entrydsm.user.domain.admin.adapter.out.persistence

import hs.kr.entrydsm.user.domain.admin.adapter.out.mapper.AdminMapper
import hs.kr.entrydsm.user.domain.admin.adapter.out.persistence.repository.AdminRepository
import hs.kr.entrydsm.user.domain.admin.application.port.out.AdminPort
import hs.kr.entrydsm.user.domain.admin.model.Admin
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.UUID

/**
 * 관리자 데이터의 영속성 처리를 담당하는 어댑터 클래스입니다.
 */
@Component
class AdminPersistenceAdapter(
    private val adminRepository: AdminRepository,
    private val adminMapper: AdminMapper,
) : AdminPort {
    /**
     * UUID를 이용하여 관리자 정보를 조회합니다.
     *
     * @param id 조회할 관리자의 UUID
     * @return 조회된 관리자 정보, 존재하지 않을 경우 null
     */
    override fun findById(id: UUID): Admin? {
        return adminRepository.findByIdOrNull(id)?.let { adminMapper.toModel(it) }
    }

    /**
     * 관리자 ID를 이용하여 관리자 정보를 조회합니다.
     *
     * @param adminId 조회할 관리자의 ID
     * @return 조회된 관리자 정보, 존재하지 않을 경우 null
     */
    override fun findByAdminId(adminId: String): Admin? {
        return adminRepository.findByAdminId(adminId)?.let { adminMapper.toModel(it) }
    }

    /**
     * 관리자 정보를 저장합니다.
     *
     * @param admin 저장할 관리자 정보
     */
    override fun save(admin: Admin) {
        adminRepository.save(adminMapper.toEntity(admin))
    }
}
