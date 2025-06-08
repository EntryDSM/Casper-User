package hs.kr.entrydsm.user.domain.auth.exception

import hs.kr.entrydsm.user.global.error.exception.EquusException
import hs.kr.entrydsm.user.global.error.exception.ErrorCode

object InvalidOkCertConnectException : EquusException(
    ErrorCode.INVALID_OKCERT_CONNECTION,
)
