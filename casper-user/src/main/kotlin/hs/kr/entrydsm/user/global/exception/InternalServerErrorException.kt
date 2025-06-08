package hs.kr.entrydsm.user.global.exception

import hs.kr.entrydsm.user.global.error.exception.EquusException
import hs.kr.entrydsm.user.global.error.exception.ErrorCode

object InternalServerErrorException : EquusException(
    ErrorCode.INTERNAL_SERVER_ERROR,
)
