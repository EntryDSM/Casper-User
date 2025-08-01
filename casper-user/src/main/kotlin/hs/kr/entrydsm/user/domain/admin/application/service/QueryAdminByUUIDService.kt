package hs.kr.entrydsm.user.domain.admin.application.service

import hs.kr.entrydsm.user.infrastructure.grpc.server.dto.InternalAdminResponse
import hs.kr.entrydsm.user.domain.admin.application.port.`in`.QueryAdminByUUIDUseCase
import hs.kr.entrydsm.user.domain.admin.application.port.out.QueryAdminPort
import hs.kr.entrydsm.user.domain.admin.exception.AdminNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

/**
 * UUID로 관리자 조회 비즈니스 로직을 처리하는 서비스 클래스입니다.
 */
@Service
class QueryAdminByUUIDService(
    private val queryAdminPort: QueryAdminPort,
) : QueryAdminByUUIDUseCase {
    /**
     * UUID를 이용하여 관리자 정보를 조회합니다.
     */
    @Transactional(readOnly = true)
    override fun queryByUUID(adminId: UUID): InternalAdminResponse {
        val admin = queryAdminPort.findById(adminId) ?: throw AdminNotFoundException
        return InternalAdminResponse(
            id = admin.id!!,
        )
    }
}
