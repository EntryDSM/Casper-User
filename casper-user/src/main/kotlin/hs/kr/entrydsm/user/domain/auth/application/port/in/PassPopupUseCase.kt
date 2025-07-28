package hs.kr.entrydsm.user.domain.auth.application.port.`in`

import hs.kr.entrydsm.user.domain.auth.adapter.`in`.web.dto.request.PassPopupRequest

/**
 * 패스 팝업 생성 기능을 정의하는 UseCase 인터페이스입니다.
 */
interface PassPopupUseCase {
    /**
     * 패스 인증 팝업을 생성합니다.
     */
    fun generatePopup(request: PassPopupRequest): String
}
