package hs.kr.entrydsm.user.domain.user.application.port.`in`

import hs.kr.entrydsm.user.infrastructure.grpc.server.dto.InternalUserResponse
import java.util.UUID

/**
 * UUID로 사용자 조회 유스케이스 인터페이스입니다.
 * 고유 식별자를 통한 사용자 정보 조회 기능을 정의합니다.
 */
interface QueryUserByUUIDUseCase {
    /**
     * UUID를 이용하여 사용자 정보를 조회합니다.
     *
     * @param userId 조회할 사용자의 UUID
     * @return 사용자 정보 응답
     */
    fun getUserById(userId: UUID): InternalUserResponse
}
