package hs.kr.entrydsm.user.domain.admin.exception

import hs.kr.entrydsm.user.global.error.exception.EquusException
import hs.kr.entrydsm.user.global.error.exception.ErrorCode

object AdminNotFoundException : EquusException(
    ErrorCode.ADMIN_NOT_FOUND,
)
