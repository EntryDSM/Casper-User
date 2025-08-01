package hs.kr.entrydsm.user.domain.admin.service

import hs.kr.entrydsm.user.global.security.jwt.JwtTokenProvider
import hs.kr.entrydsm.user.global.utils.token.dto.TokenResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminTokenRefreshService(
    private val jwtTokenProvider: JwtTokenProvider,
) {
    @Transactional
    fun execute(refreshToken: String): TokenResponse = jwtTokenProvider.reIssue(refreshToken)
}
