package hs.kr.entrydsm.user.global.error.exception

/**
 * 애플리케이션에서 발생하는 오류 코드를 정의하는 열거형 클래스입니다.
 * HTTP 상태 코드와 에러 메시지를 함께 관리합니다.
 *
 * @property status HTTP 상태 코드
 * @property message 에러 메시지
 */
enum class ErrorCode(
    val status: Int,
    val message: String,
) {
    INVALID_TOKEN(401, "Invalid Token"),
    EXPIRED_TOKEN(401, "Expired Token"),
    INVALID_URL(401, "Invalid Url"),
    INVALID_PASS(401, "Invalid Pass"),
    INVALID_USER_PASSWORD(401, "Invalid User Password"),
    ADMIN_UNAUTHORIZED(401, "Admin UnAuthorized"),
    PASSWORD_MISS_MATCH(401, "비밀번호가 일치하지 않습니다"),

    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    INVALID_OKCERT_CONNECTION(500, "Invalid OkCert Connection"),

    USER_NOT_FOUND(404, "User Not Found"),
    PASS_INFO_NOT_FOUND(404, "Pass Info Not Found"),
    ADMIN_NOT_FOUND(404, "Admin Not Found"),

    USER_ALREADY_EXISTS(409, "User Already Exists"),
}
