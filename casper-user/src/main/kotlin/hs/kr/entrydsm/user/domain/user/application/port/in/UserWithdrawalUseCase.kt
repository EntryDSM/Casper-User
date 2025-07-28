package hs.kr.entrydsm.user.domain.user.application.port.`in`

import hs.kr.entrydsm.user.domain.user.adapter.`in`.web.dto.request.WithdrawalRequest

/**
 * 사용자 탈퇴 유스케이스 인터페이스입니다.
 * 사용자 계정 탈퇴 처리를 정의합니다.
 */
interface UserWithdrawalUseCase {
    /**
     * 사용자 탈퇴를 처리합니다.
     *
     * @param request 탈퇴 요청 정보
     */
    fun withdrawal(request: WithdrawalRequest)
}
