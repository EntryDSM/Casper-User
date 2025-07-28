package hs.kr.entrydsm.user.domain.admin.application.port.`in`

import hs.kr.entrydsm.user.global.utils.token.dto.TokenResponse

/**
 * 관리자 토큰 갱신 기능을 정의하는 UseCase 인터페이스입니다.
 */
interface AdminTokenRefreshUseCase {
    /**
     * 관리자 토큰을 갱신합니다.
     */
    fun refresh(token: String): TokenResponse
}
