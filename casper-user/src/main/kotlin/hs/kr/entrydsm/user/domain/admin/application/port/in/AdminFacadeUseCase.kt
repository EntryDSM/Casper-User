package hs.kr.entrydsm.user.domain.admin.application.port.`in`

import hs.kr.entrydsm.user.domain.admin.model.Admin

/**
 * 관리자 Facade 기능을 정의하는 UseCase 인터페이스입니다.
 */
interface AdminFacadeUseCase {
    /**
     * 현재 인증된 관리자의 사용자 정보를 조회합니다.
     */
    fun getCurrentUser(): Admin

    /**
     * 관리자 ID로 사용자 정보를 조회합니다.
     */
    fun getUserById(adminId: String): Admin
}
