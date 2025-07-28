package hs.kr.entrydsm.user.domain.user.application.port.`in`

import hs.kr.entrydsm.user.domain.user.adapter.`in`.web.dto.request.UserSignupRequest
import hs.kr.entrydsm.user.global.utils.token.dto.TokenResponse

/**
 * 사용자 회원가입 유스케이스 인터페이스입니다.
 * 새로운 사용자의 회원가입 처리를 정의합니다.
 */
interface UserSignupUseCase {
    /**
     * 사용자 회원가입을 처리합니다.
     *
     * @param request 회원가입 요청 정보
     * @return 생성된 인증 토큰 응답
     */
    fun signup(request: UserSignupRequest): TokenResponse
}
