package hs.kr.entrydsm.user.global.exception

import hs.kr.entrydsm.user.global.error.exception.EquusException
import hs.kr.entrydsm.user.global.error.exception.ErrorCode

/**
 * 토큰이 만료되었을 때 발생하는 예외입니다.
 * JWT 토큰의 유효 기간이 만료된 경우 사용됩니다.
 */
object ExpiredTokenException : EquusException(
    ErrorCode.EXPIRED_TOKEN,
)
