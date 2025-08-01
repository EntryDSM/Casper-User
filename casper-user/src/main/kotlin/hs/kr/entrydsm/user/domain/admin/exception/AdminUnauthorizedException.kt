package hs.kr.entrydsm.user.domain.admin.exception

import hs.kr.entrydsm.user.global.error.exception.EquusException
import hs.kr.entrydsm.user.global.error.exception.ErrorCode

/**
 * 관리자 권한이 없을 때 발생하는 예외입니다.
 */
object AdminUnauthorizedException : EquusException(
    ErrorCode.ADMIN_UNAUTHORIZED,
)
