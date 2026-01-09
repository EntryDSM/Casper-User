package hs.kr.entrydsm.user.domain.user.application.service

import hs.kr.entrydsm.user.domain.outbox.application.port.out.OutboxEventPublisherPort
import hs.kr.entrydsm.user.domain.refreshtoken.adapter.out.repository.RefreshTokenRepository
import hs.kr.entrydsm.user.domain.user.adapter.`in`.web.dto.request.WithdrawalRequest
import hs.kr.entrydsm.user.domain.user.application.port.`in`.UserFacadeUseCase
import hs.kr.entrydsm.user.domain.user.application.port.`in`.UserWithdrawalUseCase
import hs.kr.entrydsm.user.domain.user.application.port.out.DeleteUserPort
import hs.kr.entrydsm.user.domain.user.application.port.out.SaveUserPort
import hs.kr.entrydsm.user.domain.user.exception.PasswordMisMatchException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 사용자 탈퇴 서비스 클래스입니다.
 * 비밀번호 확인 후 계정 비활성화 및 관련 데이터 정리를 처리합니다.
 *
 * Outbox 패턴을 사용하여 User 비활성화와 이벤트 발행의 원자성을 보장합니다.
 *
 * @property outboxEventPublisher Outbox 이벤트 발행자
 * @property deleteUserPort 사용자 삭제 포트
 * @property saveUserPort 사용자 저장 포트
 * @property userFacadeUseCase 사용자 파사드 유스케이스
 * @property refreshTokenRepository 리프레시 토큰 저장소
 * @property passwordEncoder 비밀번호 암호화 인코더
 */
@Service
class UserWithdrawalService(
    private val outboxEventPublisher: OutboxEventPublisherPort,
    private val deleteUserPort: DeleteUserPort,
    private val saveUserPort: SaveUserPort,
    private val userFacadeUseCase: UserFacadeUseCase,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val passwordEncoder: PasswordEncoder,
) : UserWithdrawalUseCase {
    /**
     * 사용자 탈퇴를 처리합니다.
     * 비밀번호 확인 후 계정을 비활성화하고 토큰을 삭제하며, Outbox를 통해 이벤트를 발행합니다.
     *
     * Outbox 패턴을 사용하여:
     * 1. User 비활성화
     * 2. RefreshToken 삭제
     * 3. Outbox 이벤트 저장 (같은 트랜잭션)
     * → 트랜잭션 커밋 후 Debezium이 Kafka로 발행
     *
     * @param request 탈퇴 요청 정보
     * @throws PasswordMisMatchException 비밀번호가 일치하지 않는 경우
     */
    @Transactional
    override fun withdrawal(request: WithdrawalRequest) {
        val user = userFacadeUseCase.getCurrentUser()

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw PasswordMisMatchException
        }

        val withdrawnUser = user.withdraw()
        saveUserPort.save(withdrawnUser)

        refreshTokenRepository.deleteById(user.id.toString())

        user.receiptCode?.let {
            outboxEventPublisher.publish(
                aggregateType = "User",
                aggregateId = user.id.toString(),
                eventType = "delete-user",
                payload = it,
            )
        }
    }
}
