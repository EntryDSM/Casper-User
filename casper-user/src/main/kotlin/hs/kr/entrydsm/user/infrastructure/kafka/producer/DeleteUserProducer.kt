package hs.kr.entrydsm.user.infrastructure.kafka.producer

/**
 * 사용자 삭제 이벤트를 Kafka로 발행하는 Producer 인터페이스입니다.
 * 사용자 탈퇴 시 다른 마이크로서비스에 알림을 전송하는 역할을 합니다.
 */
interface DeleteUserProducer {
    /**
     * 사용자 삭제 이벤트를 전송합니다.
     * 사용자의 접수번호를 포함한 삭제 이벤트를 Kafka 토픽으로 발행합니다.
     *
     * @param receiptCode 삭제된 사용자의 접수번호
     */
    fun send(receiptCode: Long)
}
