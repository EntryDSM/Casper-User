package hs.kr.entrydsm.user.infrastructure.kafka.producer

/**
 * 모든 테이블 삭제 이벤트를 Kafka로 발행하는 Producer 인터페이스입니다.
 * 관리자가 전체 데이터를 초기화할 때 다른 마이크로서비스에 알림을 전송하는 역할을 합니다.
 */
interface DeleteAllTableProducer {
    /**
     * 모든 테이블 삭제 이벤트를 전송합니다.
     * 전체 데이터 초기화 이벤트를 Kafka 토픽으로 발행합니다.
     */
    fun send()
}
