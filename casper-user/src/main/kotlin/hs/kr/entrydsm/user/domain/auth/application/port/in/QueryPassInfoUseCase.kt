package hs.kr.entrydsm.user.domain.auth.application.port.`in`

import hs.kr.entrydsm.user.domain.auth.adapter.`in`.web.dto.resopnse.QueryPassInfoResponse

/**
 * 패스 인증 정보 조회 기능을 정의하는 UseCase 인터페이스입니다.
 */
interface QueryPassInfoUseCase {
    /**
     * 토큰을 이용하여 패스 인증 정보를 조회합니다.
     */
    fun queryPassInfo(token: String?): QueryPassInfoResponse
}
