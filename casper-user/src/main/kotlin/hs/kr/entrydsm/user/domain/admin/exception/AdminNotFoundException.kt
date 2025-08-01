package hs.kr.entrydsm.user.domain.admin.exception

import hs.kr.entrydsm.user.global.error.exception.EquusException
import hs.kr.entrydsm.user.global.error.exception.ErrorCode

/**
 * 관리자를 찾을 수 없을 때 발생하는 예외입니다.
 */
object AdminNotFoundException : EquusException(
    ErrorCode.ADMIN_NOT_FOUND,
)
