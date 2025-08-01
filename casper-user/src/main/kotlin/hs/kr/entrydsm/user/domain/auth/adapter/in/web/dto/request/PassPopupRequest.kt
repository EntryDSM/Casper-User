package hs.kr.entrydsm.user.domain.auth.adapter.`in`.web.dto.request

import jakarta.validation.constraints.NotBlank

/**
 * 패스 팝업 생성 요청 데이터를 담는 DTO 클래스입니다.
 */
data class PassPopupRequest(
    @NotBlank(message = "redirect_url은 Null 또는 공백 또는 띄어쓰기를 허용하지 않습니다.")
    val redirectUrl: String,
)
