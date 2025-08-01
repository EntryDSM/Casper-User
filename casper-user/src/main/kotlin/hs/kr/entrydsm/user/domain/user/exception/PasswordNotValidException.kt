package hs.kr.entrydsm.user.domain.user.exception

import hs.kr.entrydsm.user.global.error.exception.EquusException
import hs.kr.entrydsm.user.global.error.exception.ErrorCode

/**
 * 패스워드가 유효하지 않을 때 발생하는 예외입니다.
 * 로그인 시 입력한 비밀번호가 잘못된 경우 사용됩니다.
 */
object PasswordNotValidException : EquusException(
    ErrorCode.INVALID_USER_PASSWORD,
)
