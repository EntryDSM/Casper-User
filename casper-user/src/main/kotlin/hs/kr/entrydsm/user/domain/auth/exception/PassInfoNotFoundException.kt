package hs.kr.entrydsm.user.domain.auth.exception

import hs.kr.entrydsm.user.global.error.exception.EquusException
import hs.kr.entrydsm.user.global.error.exception.ErrorCode

/**
 * Pass 인증 정보를 찾을 수 없을 때 발생하는 예외입니다.
 * Pass 인증을 통해 저장된 사용자 정보가 만료되었거나 존재하지 않는 경우 사용됩니다.
 */
object PassInfoNotFoundException : EquusException(
    ErrorCode.PASS_INFO_NOT_FOUND,
)
