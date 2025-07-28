package hs.kr.entrydsm.user.domain.admin.application.port.`in`

import hs.kr.entrydsm.user.domain.admin.adapter.`in`.web.dto.response.InternalAdminResponse
import java.util.UUID

/**
 * UUID로 관리자 조회 기능을 정의하는 UseCase 인터페이스입니다.
 */
interface QueryAdminByUUIDUseCase {
    /**
     * UUID로 관리자 정보를 조회합니다.
     */
    fun queryByUUID(adminId: UUID): InternalAdminResponse
}
