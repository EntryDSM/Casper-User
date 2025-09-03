package hs.kr.entrydsm.user.domain.user.application.service

import hs.kr.entrydsm.user.domain.user.application.port.`in`.ChangeReceiptCodeUseCase
import hs.kr.entrydsm.user.domain.user.application.port.out.QueryUserPort
import hs.kr.entrydsm.user.domain.user.application.port.out.SaveUserPort
import hs.kr.entrydsm.user.domain.user.exception.UserNotFoundException
import hs.kr.entrydsm.user.infrastructure.kafka.producer.UserEventProducer
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

/**
 * 사용자 접수코드 변경 서비스 클래스입니다.
 * 지원서 접수 시 사용자에게 할당된 접수번호를 업데이트하는 처리를 담당합니다.
 *
 * @property queryUserPort 사용자 조회 포트
 * @property saveUserPort 사용자 저장 포트
 * @property userEventProducer 사용자 이벤트 발행기
 */
@Service
class ChangeReceiptCodeService(
    private val queryUserPort: QueryUserPort,
    private val saveUserPort: SaveUserPort,
    private val userEventProducer: UserEventProducer
) : ChangeReceiptCodeUseCase {
    /**
     * 사용자의 접수코드를 변경합니다.
     *
     * @param userId 사용자 ID
     * @param receiptCode 새로운 접수코드
     * @throws UserNotFoundException 사용자가 존재하지 않는 경우
     */
    @Transactional
    override fun changeReceiptCode(
        userId: UUID,
        receiptCode: Long,
    ) {
        try {
            val user = queryUserPort.findById(userId)
            
            if (user == null) {
                userEventProducer.sendReceiptCodeUpdateFailed(
                    receiptCode = receiptCode,
                    userId = userId,
                    reason = "User not found"
                )
                throw UserNotFoundException
            }

            val updatedUser = user.copy(receiptCode = receiptCode)
            saveUserPort.save(updatedUser)

            // 성공 이벤트 발행
            userEventProducer.sendReceiptCodeUpdateCompleted(receiptCode, userId)
            
        } catch (e: Exception) {

            if (e !is UserNotFoundException) {
                userEventProducer.sendReceiptCodeUpdateFailed(
                    receiptCode = receiptCode,
                    userId = userId,
                    reason = e.message ?: "Unknown error"
                )
            }
            throw e  // 예외 다시 던져서 롤백 발생
        }
    }
}
