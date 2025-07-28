package hs.kr.entrydsm.user.domain.user.application.port.`in`

import hs.kr.entrydsm.user.domain.user.adapter.`in`.web.dto.response.UserResponse

/**
 * 사용자 정보 조회 유스케이스 인터페이스입니다.
 * 현재 인증된 사용자의 개인정보를 조회하는 기능을 정의합니다.
 */
interface QueryUserInfoUseCase {
    /**
     * 현재 로그인한 사용자의 정보를 조회합니다.
     *
     * @return 사용자 정보 응답
     */
    fun getUserInfo(): UserResponse
}
