package hs.kr.entrydsm.user.domain.user.exception

import hs.kr.entrydsm.user.global.error.exception.EquusException
import hs.kr.entrydsm.user.global.error.exception.ErrorCode

/**
 * 비밀번호가 일치하지 않을 때 발생하는 예외입니다.
 * 사용자가 입력한 비밀번호가 저장된 비밀번호와 다른 경우 사용됩니다.
 */
object PasswordMisMatchException : EquusException(
    ErrorCode.PASSWORD_MISS_MATCH,
)
