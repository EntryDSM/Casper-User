package hs.kr.entrydsm.user.domain.user.application.port.`in`

import hs.kr.entrydsm.user.domain.user.adapter.`in`.web.dto.request.ReactivateRequest

/**
 * 사용자 계정 재활성화 유스케이스 인터페이스입니다.
 * 탈퇴한 사용자의 계정을 다시 활성화하는 기능을 정의합니다.
 */
interface UserReactivationUseCase {
    /**
     * 탈퇴한 사용자의 계정을 재활성화합니다.
     *
     * @param request 재활성화 요청 정보
     */
    fun reactivateWithdrawnUser(request: ReactivateRequest)
}
