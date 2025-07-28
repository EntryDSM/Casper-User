package hs.kr.entrydsm.user.domain.auth.exception

import hs.kr.entrydsm.user.global.error.exception.EquusException
import hs.kr.entrydsm.user.global.error.exception.ErrorCode

/**
 * Pass 인증이 유효하지 않을 때 발생하는 예외입니다.
 * Pass 인증 과정에서 검증에 실패한 경우 사용됩니다.
 */
object InvalidPassException : EquusException(
    ErrorCode.INVALID_PASS,
)
