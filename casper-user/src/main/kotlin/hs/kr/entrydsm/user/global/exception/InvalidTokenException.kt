package hs.kr.entrydsm.user.global.exception

import hs.kr.entrydsm.user.global.error.exception.EquusException
import hs.kr.entrydsm.user.global.error.exception.ErrorCode

object InvalidTokenException : EquusException(
    ErrorCode.INVALID_TOKEN,
)
