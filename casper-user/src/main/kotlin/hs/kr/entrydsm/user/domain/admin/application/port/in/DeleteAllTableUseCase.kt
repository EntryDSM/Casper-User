package hs.kr.entrydsm.user.domain.admin.application.port.`in`

/**
 * 모든 테이블 삭제 기능을 정의하는 UseCase 인터페이스입니다.
 */
interface DeleteAllTableUseCase {
    /**
     * 모든 테이블을 삭제합니다.
     */
    fun deleteAllTables()
}
