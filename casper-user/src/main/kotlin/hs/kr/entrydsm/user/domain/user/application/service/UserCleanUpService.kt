package hs.kr.entrydsm.user.domain.user.application.service

import hs.kr.entrydsm.user.domain.user.application.port.`in`.UserCleanUpUseCase
import hs.kr.entrydsm.user.domain.user.application.port.out.DeleteUserPort
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 사용자 데이터 정리 서비스 클래스입니다.
 * 일정 기간이 지난 탈퇴 사용자 데이터를 자동으로 삭제하여 개인정보보호법을 준수합니다.
 *
 * @property deleteUserPort 사용자 삭제 포트
 */
@Service
class UserCleanUpService(
    private val deleteUserPort: DeleteUserPort,
) : UserCleanUpUseCase {
    /**
     * 탈퇴한 지 7일이 지난 사용자 데이터를 정리합니다.
     * 매일 새벽 2시에 자동으로 실행되는 스케줄러입니다.
     */
    @Transactional
    @Scheduled(cron = "0 0 2 * * *")
    override fun cleanupWithdrawnUsers() {
        val usersToDelete = deleteUserPort.findWithdrawnUsersOlderThan(7)

        usersToDelete.forEach { user ->
            deleteUserPort.deleteById(user.id!!)
        }
    }
}
