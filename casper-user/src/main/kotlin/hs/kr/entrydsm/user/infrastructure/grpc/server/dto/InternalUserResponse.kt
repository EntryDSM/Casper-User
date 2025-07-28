package hs.kr.entrydsm.user.infrastructure.grpc.server.dto

import hs.kr.entrydsm.user.domain.user.adapter.out.domain.UserRole
import java.util.UUID

/**
 * 내부 gRPC 통신용 사용자 응답 DTO 클래스입니다.
 * 다른 마이크로서비스와의 통신에서 사용자 정보를 전달하는 데 사용됩니다.
 *
 * @property id 사용자 고유 식별자
 * @property phoneNumber 사용자 전화번호
 * @property name 사용자 이름
 * @property isParent 학부모 여부
 * @property receiptCode 지원서 접수번호
 * @property role 사용자 역할
 */
data class InternalUserResponse(
    val id: UUID,
    val phoneNumber: String,
    val name: String,
    val isParent: Boolean,
    val receiptCode: Long?,
    val role: UserRole,
)
