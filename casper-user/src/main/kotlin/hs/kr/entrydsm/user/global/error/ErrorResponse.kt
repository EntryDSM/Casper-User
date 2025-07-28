package hs.kr.entrydsm.user.global.error

/**
 * API 오류 응답을 나타내는 데이터 클래스입니다.
 * 클라이언트에게 일관된 형식의 오류 정보를 제공합니다.
 *
 * @property status HTTP 상태 코드
 * @property message 오류 메시지
 */
data class ErrorResponse(
    val status: Int,
    val message: String?,
)
