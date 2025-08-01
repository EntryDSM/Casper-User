package hs.kr.entrydsm.user.domain.user.adapter.`in`.web.dto.request

import jakarta.validation.constraints.NotBlank

data class ReactivateRequest(
    @NotBlank
    val phoneNumber: String,
)
