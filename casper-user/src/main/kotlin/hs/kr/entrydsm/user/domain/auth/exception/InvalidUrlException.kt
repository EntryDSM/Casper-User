package hs.kr.entrydsm.user.domain.auth.exception

import hs.kr.entrydsm.user.global.error.exception.EquusException
import hs.kr.entrydsm.user.global.error.exception.ErrorCode

/**
 * URL이 유효하지 않을 때 발생하는 예외입니다.
 */
object InvalidUrlException : EquusException(
    ErrorCode.INVALID_URL,
)
