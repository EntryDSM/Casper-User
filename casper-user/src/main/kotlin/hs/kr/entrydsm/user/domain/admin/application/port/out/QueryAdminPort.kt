package hs.kr.entrydsm.user.domain.admin.application.port.out

import hs.kr.entrydsm.user.domain.admin.model.Admin
import java.util.UUID

/**
 * 관리자 조회 작업을 위한 포트 인터페이스입니다.
 * 헥사고날 아키텍처에서 도메인 계층이 인프라스트럭처 계층과 통신하기 위한 인터페이스입니다.
 */
interface QueryAdminPort {
    /**
     * 관리자 ID로 관리자를 조회합니다.
     *
     * @param adminId 조회할 관리자 ID
     * @return 조회된 관리자 도메인 모델 (없으면 null)
     */
    fun findByAdminId(adminId: String): Admin?

    /**
     * UUID로 관리자를 조회합니다.
     *
     * @param id 조회할 관리자 UUID
     * @return 조회된 관리자 도메인 모델 (없으면 null)
     */
    fun findById(id: UUID): Admin?
}
