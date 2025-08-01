package hs.kr.entrydsm.user.domain.admin.adapter.`in`.web.dto.request

import jakarta.validation.constraints.NotBlank

/**
 * 관리자 로그인 요청 데이터를 담는 DTO 클래스입니다.
 */
data class AdminLoginRequest(
    @NotBlank
    val adminId: String,
    @NotBlank
    val password: String,
)
