package hs.kr.entrydsm.user.domain.auth.exception

import hs.kr.entrydsm.user.global.error.exception.EquusException
import hs.kr.entrydsm.user.global.error.exception.ErrorCode

object PassInfoNotFoundException : EquusException(
    ErrorCode.PASS_INFO_NOT_FOUND,
)
