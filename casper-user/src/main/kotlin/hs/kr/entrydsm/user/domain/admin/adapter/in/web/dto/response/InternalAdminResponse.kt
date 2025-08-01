package hs.kr.entrydsm.user.domain.admin.adapter.`in`.web.dto.response

import java.util.UUID

/**
 * 내부 시스템 간 관리자 정보 응답 데이터를 담는 DTO 클래스입니다.
 */
data class InternalAdminResponse(
    val id: UUID,
)
