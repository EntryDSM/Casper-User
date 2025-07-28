package hs.kr.entrydsm.user.domain.user.application.port.`in`

import hs.kr.entrydsm.user.domain.user.model.User

/**
 * 사용자 파사드 유스케이스 인터페이스입니다.
 * 여러 계층에서 공통으로 사용되는 사용자 조회 기능을 정의합니다.
 */
interface UserFacadeUseCase {
    /**
     * 현재 인증된 사용자를 조회합니다.
     *
     * @return 현재 인증된 사용자
     */
    fun getCurrentUser(): User

    /**
     * 전화번호로 사용자를 조회합니다.
     *
     * @param phoneNumber 조회할 전화번호
     * @return 조회된 사용자
     */
    fun getUserByPhoneNumber(phoneNumber: String): User
}
