package hs.kr.entrydsm.user.domain.admin.presentation.dto.request

import jakarta.validation.constraints.NotBlank

data class AdminLoginRequest(
    @NotBlank
    val adminId: String,
    @NotBlank
    val password: String,
)
