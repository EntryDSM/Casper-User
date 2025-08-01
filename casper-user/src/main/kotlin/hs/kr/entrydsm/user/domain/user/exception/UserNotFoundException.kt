package hs.kr.entrydsm.user.domain.user.exception

import hs.kr.entrydsm.user.global.error.exception.EquusException
import hs.kr.entrydsm.user.global.error.exception.ErrorCode

/**
 * 사용자를 찾을 수 없을 때 발생하는 예외입니다.
 * 요청한 사용자 정보가 시스템에 존재하지 않는 경우 사용됩니다.
 */
object UserNotFoundException : EquusException(
    ErrorCode.USER_NOT_FOUND,
)
