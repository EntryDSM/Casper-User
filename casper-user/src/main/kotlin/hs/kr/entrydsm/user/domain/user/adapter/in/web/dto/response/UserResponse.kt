package hs.kr.entrydsm.user.domain.user.adapter.`in`.web.dto.response

/**
 * 사용자 정보 응답 데이터를 담는 DTO 클래스입니다.
 */
data class UserResponse(
    val name: String,
    val phoneNumber: String,
    val isParent: Boolean,
)
