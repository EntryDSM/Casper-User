package hs.kr.entrydsm.user.domain.user.presentation.dto.response

data class UserResponse(
    val name: String,
    val phoneNumber: String,
    val isParent: Boolean,
)
