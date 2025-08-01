package hs.kr.entrydsm.user.domain.user.exception

import hs.kr.entrydsm.user.global.error.exception.CasperException
import hs.kr.entrydsm.user.global.error.exception.ErrorCode

/**
 * 사용자가 이미 존재할 때 발생하는 예외입니다.
 * 회원가입 시 이미 등록된 전화번호로 가입을 시도하는 경우 사용됩니다.
 */
object UserAlreadyExistsException : CasperException(
    ErrorCode.USER_ALREADY_EXISTS,
)
