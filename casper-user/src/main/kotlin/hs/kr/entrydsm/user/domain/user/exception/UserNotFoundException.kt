package hs.kr.entrydsm.user.domain.user.exception

import hs.kr.entrydsm.user.global.error.exception.EquusException
import hs.kr.entrydsm.user.global.error.exception.ErrorCode

object UserNotFoundException : EquusException(
    ErrorCode.USER_NOT_FOUND,
)
