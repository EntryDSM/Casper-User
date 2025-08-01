package hs.kr.entrydsm.user.domain.user.application.port.`in`

import java.util.UUID

/**
 * 사용자 접수코드 변경 유스케이스 인터페이스입니다.
 * 사용자의 지원서 접수번호를 업데이트하는 기능을 정의합니다.
 */
interface ChangeReceiptCodeUseCase {
    /**
     * 사용자의 접수코드를 변경합니다.
     *
     * @param userId 사용자 ID
     * @param receiptCode 새로운 접수코드
     */
    fun changeReceiptCode(
        userId: UUID,
        receiptCode: Long,
    )
}
