package hs.kr.entrydsm.user.domain.outbox.model

import java.time.LocalDateTime

/**
 * Outbox 이벤트 도메인 모델
 *
 * Debezium CDC를 통해 Kafka로 발행될 이벤트를 저장합니다.
 * 도메인 엔티티와 동일 트랜잭션으로 저장되어 원자성을 보장합니다.
 *
 * @property id 이벤트 고유 식별자
 * @property aggregateType 집계 루트 타입 (예: "User")
 * @property aggregateId 집계 식별자 (예: userId)
 * @property eventType Kafka Topic 이름 (예: "delete-user")
 * @property payload 이벤트 데이터 (JSON 직렬화)
 * @property createdAt 이벤트 생성 시각
 */
data class OutboxEvent(
    val id: Long? = null,
    val aggregateType: String,
    val aggregateId: String,
    val eventType: String,
    val payload: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
) {

    companion object {
        /**
         * OutboxEvent 생성 팩토리 메서드
         *
         * @param aggregateType 집계 루트 타입
         * @param aggregateId 집계 식별자
         * @param eventType Kafka Topic 이름
         * @param payload 이벤트 데이터 (JSON 문자열)
         * @return OutboxEvent 인스턴스
         */
        fun create(
            aggregateType: String,
            aggregateId: String,
            eventType: String,
            payload: String,
        ): OutboxEvent =
            OutboxEvent(
                aggregateType = aggregateType,
                aggregateId = aggregateId,
                eventType = eventType,
                payload = payload,
            )
    }
}
