package hs.kr.entrydsm.user.global.exception

import hs.kr.entrydsm.user.global.error.exception.EquusException
import hs.kr.entrydsm.user.global.error.exception.ErrorCode

object ExpiredTokenException : EquusException(
    ErrorCode.EXPIRED_TOKEN,
)
