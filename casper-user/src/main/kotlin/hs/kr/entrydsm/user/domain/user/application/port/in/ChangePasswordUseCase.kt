package hs.kr.entrydsm.user.domain.user.application.port.`in`

import hs.kr.entrydsm.user.domain.user.adapter.`in`.web.dto.request.ChangePasswordRequest

/**
 * 사용자 비밀번호 변경 유스케이스 인터페이스입니다.
 * 기존 비밀번호 확인 후 새로운 비밀번호로 변경하는 기능을 정의합니다.
 */
interface ChangePasswordUseCase {
    /**
     * 사용자의 비밀번호를 변경합니다.
     *
     * @param request 비밀번호 변경 요청 정보
     */
    fun changePassword(request: ChangePasswordRequest)
}
