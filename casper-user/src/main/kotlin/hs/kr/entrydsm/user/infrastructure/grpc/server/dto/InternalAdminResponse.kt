package hs.kr.entrydsm.user.infrastructure.grpc.server.dto

import java.util.UUID

/**
 * 내부 시스템 간 관리자 정보 응답 데이터를 담는 DTO 클래스입니다.
 *
 * @property id 고유 식별자
 */
data class InternalAdminResponse(
    val id: UUID,
)
