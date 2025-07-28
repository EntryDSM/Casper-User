package hs.kr.entrydsm.user.domain.admin.application.port.out

import hs.kr.entrydsm.user.domain.admin.model.Admin

/**
 * 관리자 저장 작업을 위한 포트 인터페이스입니다.
 * 헥사고날 아키텍처에서 도메인 계층이 인프라스트럭처 계층과 통신하기 위한 인터페이스입니다.
 */
interface SaveAdminPort {
    /**
     * 관리자 정보를 저장합니다.
     *
     * @param admin 저장할 관리자 도메인 모델
     */
    fun save(admin: Admin)
}
