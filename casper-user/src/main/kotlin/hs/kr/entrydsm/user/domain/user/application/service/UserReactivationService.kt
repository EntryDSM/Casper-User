package hs.kr.entrydsm.user.domain.user.application.service

import hs.kr.entrydsm.user.domain.user.adapter.`in`.web.dto.request.ReactivateRequest
import hs.kr.entrydsm.user.domain.user.application.port.`in`.UserReactivationUseCase
import hs.kr.entrydsm.user.domain.user.application.port.out.QueryUserPort
import hs.kr.entrydsm.user.domain.user.application.port.out.SaveUserPort
import hs.kr.entrydsm.user.domain.user.exception.UserAlreadyExistsException
import hs.kr.entrydsm.user.domain.user.exception.UserNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 사용자 계정 재활성화 서비스 클래스입니다.
 * 탈퇴한 사용자의 계정을 다시 활성화하는 처리를 담당합니다.
 *
 * @property queryUserPort 사용자 조회 포트
 * @property saveUserPort 사용자 저장 포트
 */
@Service
class UserReactivationService(
    private val queryUserPort: QueryUserPort,
    private val saveUserPort: SaveUserPort,
) : UserReactivationUseCase {
    /**
     * 탈퇴한 사용자의 계정을 재활성화합니다.
     * 전화번호로 탈퇴한 사용자를 찾아 계정을 다시 활성화합니다.
     *
     * @param request 재활성화 요청 정보
     * @throws UserNotFoundException 사용자가 존재하지 않는 경우
     * @throws UserAlreadyExistsException 이미 활성화된 계정인 경우
     */
    @Transactional
    override fun reactivateWithdrawnUser(request: ReactivateRequest) {
        val existingUser =
            queryUserPort.findByPhoneNumber(request.phoneNumber)
                ?: throw UserNotFoundException

        if (existingUser.active) {
            throw UserAlreadyExistsException
        }

        val reactivatedUser = existingUser.reactivate()
        saveUserPort.save(reactivatedUser)
    }
}
