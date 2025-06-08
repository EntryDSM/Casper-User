package hs.kr.entrydsm.user.domain.user.exception

import hs.kr.entrydsm.user.global.error.exception.EquusException
import hs.kr.entrydsm.user.global.error.exception.ErrorCode

object UserAlreadyExistsException : EquusException(
    ErrorCode.USER_ALREADY_EXISTS,
)
