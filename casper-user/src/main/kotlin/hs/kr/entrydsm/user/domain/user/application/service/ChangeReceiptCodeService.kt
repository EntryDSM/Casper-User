package hs.kr.entrydsm.user.domain.user.application.service

import hs.kr.entrydsm.user.domain.user.application.port.`in`.ChangeReceiptCodeUseCase
import hs.kr.entrydsm.user.domain.user.application.port.out.QueryUserPort
import hs.kr.entrydsm.user.domain.user.application.port.out.SaveUserPort
import hs.kr.entrydsm.user.domain.user.exception.UserNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

/**
 * 사용자 접수코드 변경 서비스 클래스입니다.
 * 지원서 접수 시 사용자에게 할당된 접수번호를 업데이트하는 처리를 담당합니다.
 *
 * @property queryUserPort 사용자 조회 포트
 * @property saveUserPort 사용자 저장 포트
 */
@Transactional
@Service
class ChangeReceiptCodeService(
    private val queryUserPort: QueryUserPort,
    private val saveUserPort: SaveUserPort,
) : ChangeReceiptCodeUseCase {
    /**
     * 사용자의 접수코드를 변경합니다.
     *
     * @param userId 사용자 ID
     * @param receiptCode 새로운 접수코드
     * @throws UserNotFoundException 사용자가 존재하지 않는 경우
     */
    override fun changeReceiptCode(
        userId: UUID,
        receiptCode: Long,
    ) {
        val user = queryUserPort.findById(userId) ?: throw UserNotFoundException
        val updateUser = user.changeReceiptCode(receiptCode)
        saveUserPort.save(updateUser)
    }
}
