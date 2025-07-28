package hs.kr.entrydsm.user.domain.user.application.port.out

import hs.kr.entrydsm.user.domain.user.model.User

/**
 * 사용자 저장 작업을 위한 포트 인터페이스입니다.
 * 헥사고날 아키텍처에서 도메인 계층이 인프라스트럭처 계층과 통신하기 위한 인터페이스입니다.
 */
interface SaveUserPort {
    /**
     * 사용자 정보를 저장합니다.
     *
     * @param user 저장할 사용자 도메인 모델
     * @return 저장된 사용자 도메인 모델
     */
    fun save(user: User): User
}
