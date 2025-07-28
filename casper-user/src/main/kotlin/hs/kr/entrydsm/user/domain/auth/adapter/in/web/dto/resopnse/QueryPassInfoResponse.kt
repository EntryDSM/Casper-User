package hs.kr.entrydsm.user.domain.auth.adapter.`in`.web.dto.resopnse

/**
 * 패스 인증 정보 조회 응답 데이터를 담는 DTO 클래스입니다.
 */
data class QueryPassInfoResponse(
    val phoneNumber: String,
    val name: String,
)
