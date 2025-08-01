package hs.kr.entrydsm.user.global.utils.token.dto

/**
 * JWT 토큰 응답을 나타내는 데이터 클래스입니다.
 * 로그인 및 토큰 갱신 시 클라이언트에게 반환되는 토큰 정보를 담습니다.
 *
 * @property accessToken 액세스 토큰
 * @property refreshToken 리프레시 토큰
 */
data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
)
