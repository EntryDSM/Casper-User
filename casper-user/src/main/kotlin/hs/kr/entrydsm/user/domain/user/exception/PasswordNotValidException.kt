package hs.kr.entrydsm.user.domain.user.exception

import hs.kr.entrydsm.user.global.error.exception.EquusException
import hs.kr.entrydsm.user.global.error.exception.ErrorCode

object PasswordNotValidException : EquusException(
    ErrorCode.INVALID_USER_PASSWORD,
)
