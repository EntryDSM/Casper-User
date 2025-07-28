package hs.kr.entrydsm.user.domain.user.application.port.`in`

import hs.kr.entrydsm.user.global.utils.token.dto.TokenResponse

/**
 * 사용자 토큰 갱신 유스케이스 인터페이스입니다.
 * 만료된 액세스 토큰을 리프레시 토큰으로 갱신하는 기능을 정의합니다.
 */
interface UserTokenRefreshUseCase {
    /**
     * 리프레시 토큰을 이용하여 새로운 액세스 토큰을 발급합니다.
     *
     * @param refreshToken 기존 리프레시 토큰
     * @return 새로 발급된 토큰 응답
     */
    fun refresh(refreshToken: String): TokenResponse
}
