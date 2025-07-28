package hs.kr.entrydsm.user.domain.user.application.port.`in`

import hs.kr.entrydsm.user.domain.user.adapter.`in`.web.dto.request.UserLoginRequest
import hs.kr.entrydsm.user.global.utils.token.dto.TokenResponse

/**
 * 사용자 로그인 유스케이스 인터페이스입니다.
 * 사용자 인증 및 토큰 발급 처리를 정의합니다.
 */
interface UserLoginUseCase {
    /**
     * 사용자 로그인을 처리합니다.
     *
     * @param request 로그인 요청 정보
     * @return 생성된 인증 토큰 응답
     */
    fun login(request: UserLoginRequest): TokenResponse
}
