package hs.kr.entrydsm.user.domain.auth.exception

import hs.kr.entrydsm.user.global.error.exception.EquusException
import hs.kr.entrydsm.user.global.error.exception.ErrorCode

object InvalidUrlException : EquusException(
    ErrorCode.INVALID_URL,
)
