package hs.kr.entrydsm.user.domain.user.adapter.`in`.web.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

/**
 * 사용자 로그인 요청 데이터를 담는 DTO 클래스입니다.
 */
data class UserLoginRequest(
    @NotBlank(message = "phone_number은 Null 또는 공백 또는 띄어쓰기를 허용하지 않습니다.")
    val phoneNumber: String,
    @NotBlank(message = "password는 Null 또는 공백 또는 띄어쓰기를 허용하지 않습니다.")
    @Pattern(
        regexp =
            "(?=.*[a-z])(?=.*[0-9])(?=.*[!#$%&'()*+,./:;<=>?@�?_`{|}~])[a-zA-Z0-9!#$%&'()*+,./:;<=>?@�?_`{|}~]{8,32}$",
        message = "password는 소문자, 숫자, 특수문자가 포함되어야 합니다.",
    )
    val password: String,
)
