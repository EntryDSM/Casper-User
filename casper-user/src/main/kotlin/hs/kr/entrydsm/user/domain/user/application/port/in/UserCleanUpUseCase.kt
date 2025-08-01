package hs.kr.entrydsm.user.domain.user.application.port.`in`

/**
 * 사용자 데이터 정리 유스케이스 인터페이스입니다.
 * 일정 기간이 지난 탈퇴 사용자 데이터를 정리하는 기능을 정의합니다.
 */
interface UserCleanUpUseCase {
    /**
     * 탈퇴한 지 일정 기간이 지난 사용자 데이터를 정리합니다.
     * 개인정보보호법에 따른 데이터 보관 기간 준수를 위한 기능입니다.
     */
    fun cleanupWithdrawnUsers()
}
