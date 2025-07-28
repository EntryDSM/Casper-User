package hs.kr.entrydsm.user.domain.admin.facade

import hs.kr.entrydsm.user.domain.admin.application.port.`in`.AdminFacadeUseCase
import hs.kr.entrydsm.user.domain.admin.application.port.out.QueryAdminPort
import hs.kr.entrydsm.user.domain.admin.exception.AdminNotFoundException
import hs.kr.entrydsm.user.domain.admin.model.Admin
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.UUID

/**
 * 관리자 관련 공통 기능을 제공하는 Facade 클래스입니다.
 */
@Component
class AdminFacade(
    private val queryAdminPort: QueryAdminPort,
) : AdminFacadeUseCase {
    /**
     * 현재 인증된 관리자의 사용자 정보를 조회합니다.
     *
     * @return 현재 인증된 관리자 정보
     * @throws AdminNotFoundException 관리자가 존재하지 않는 경우
     */
    override fun getCurrentUser(): Admin {
        val adminId = SecurityContextHolder.getContext().authentication.name
        return queryAdminPort.findById(UUID.fromString(adminId)) ?: throw AdminNotFoundException
    }

    /**
     * 관리자 ID로 사용자 정보를 조회합니다.
     *
     * @param adminId 조회할 관리자의 UUID 문자열
     * @return 조회된 관리자 정보
     * @throws AdminNotFoundException 관리자가 존재하지 않는 경우
     */
    override fun getUserById(adminId: String): Admin {
        return queryAdminPort.findById(UUID.fromString(adminId)) ?: throw AdminNotFoundException
    }
}
