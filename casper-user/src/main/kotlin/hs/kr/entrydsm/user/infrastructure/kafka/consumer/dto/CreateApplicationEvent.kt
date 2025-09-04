package hs.kr.entrydsm.user.infrastructure.kafka.consumer.dto

import java.util.UUID

/**
 * 원서 생성 이벤트 데이터를 담는 DTO 클래스입니다.
 * 
 * 원서 서비스에서 원서가 생성되었을 때 사용자 서비스에 전달되는
 * 이벤트 데이터를 정의합니다. 사용자의 접수번호 업데이트를 위해 사용됩니다.
 * 
 * @property receiptCode 생성된 원서의 접수번호
 * @property userId 원서를 생성한 사용자의 고유 식별자
 */
data class CreateApplicationEvent(
    val receiptCode: Long,
    val userId: UUID
)
