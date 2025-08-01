package hs.kr.entrydsm.user.global.security.jwt

import hs.kr.entrydsm.user.domain.refreshtoken.adapter.out.RefreshToken
import hs.kr.entrydsm.user.domain.refreshtoken.adapter.out.repository.RefreshTokenRepository
import hs.kr.entrydsm.user.domain.user.adapter.out.domain.UserRole
import hs.kr.entrydsm.user.global.exception.ExpiredTokenException
import hs.kr.entrydsm.user.global.exception.InvalidTokenException
import hs.kr.entrydsm.user.global.security.auth.AdminDetailsService
import hs.kr.entrydsm.user.global.security.auth.AuthDetailsService
import hs.kr.entrydsm.user.global.utils.token.dto.TokenResponse
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jws
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.Date
import javax.crypto.SecretKey

/**
 * JWT 토큰의 생성, 검증, 파싱을 담당하는 클래스입니다.
 * 액세스 토큰과 리프레시 토큰을 관리하며, 토큰 기반 인증을 처리합니다.
 *
 * @property jwtProperties JWT 관련 설정 프로퍼티
 * @property authDetailsService 사용자 상세 정보 서비스
 * @property refreshTokenRepository 리프레시 토큰 저장소
 * @property adminDetailsService 관리자 상세 정보 서비스
 */
@Component
class JwtTokenProvider(
    private val jwtProperties: JwtProperties,
    private val authDetailsService: AuthDetailsService,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val adminDetailsService: AdminDetailsService,
) {
    companion object {
        private const val ACCESS_KEY = "access_token"
        private const val REFRESH_KEY = "refresh_token"
    }

    private val secretKey: SecretKey by lazy {
        Keys.hmacShaKeyFor(jwtProperties.secretKey.toByteArray(StandardCharsets.UTF_8))
    }

    /**
     * 토큰에서 클레임을 추출합니다.
     *
     * @param token 파싱할 JWT 토큰
     * @return 토큰의 클레임 정보
     * @throws InvalidTokenException 토큰이 유효하지 않은 경우
     */
    private fun getBody(token: String): Claims {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token).body
        } catch (e: JwtException) {
            throw InvalidTokenException
        }
    }

    /**
     * 토큰에서 만료 시간을 확인하고 subject를 반환합니다.
     *
     * @param token 확인할 JWT 토큰
     * @return 토큰의 subject (사용자 ID)
     * @throws ExpiredTokenException 토큰이 만료된 경우
     */
    fun getSubjectWithExpiredCheck(token: String): String {
        val body = getBody(token)
        return if (body.expiration.before(Date())) {
            throw ExpiredTokenException
        } else {
            body.subject
        }
    }

    /**
     * 리프레시 토큰을 이용하여 새로운 토큰을 발급합니다.
     *
     * @param refreshToken 기존 리프레시 토큰
     * @return 새로 발급된 토큰 응답
     * @throws InvalidTokenException 토큰이 유효하지 않은 경우
     */
    fun reIssue(refreshToken: String): TokenResponse {
        if (!isRefreshToken(refreshToken)) {
            throw InvalidTokenException
        }

        refreshTokenRepository.findByToken(refreshToken)
            ?.let { token ->
                val id = token.id
                val role = getRole(token.token)

                val tokenResponse = generateToken(id, role)
                token.update(tokenResponse.refreshToken, jwtProperties.refreshExp)
                return TokenResponse(tokenResponse.accessToken, tokenResponse.refreshToken)
            } ?: throw InvalidTokenException
    }

    /**
     * 액세스 토큰과 리프레시 토큰을 생성합니다.
     *
     * @param userId 사용자 ID
     * @param role 사용자 역할
     * @return 생성된 토큰 응답
     */
    fun generateToken(
        userId: String,
        role: String,
    ): TokenResponse {
        val accessToken = generateAccessToken(userId, role, ACCESS_KEY, jwtProperties.accessExp)
        val refreshToken = generateRefreshToken(role, REFRESH_KEY, jwtProperties.refreshExp)
        refreshTokenRepository.save(
            RefreshToken(userId, refreshToken, jwtProperties.refreshExp),
        )
        return TokenResponse(accessToken, refreshToken)
    }

    /**
     * 액세스 토큰을 생성합니다.
     *
     * @param id 사용자 ID
     * @param role 사용자 역할
     * @param type 토큰 타입
     * @param exp 만료 시간 (초)
     * @return 생성된 액세스 토큰
     */
    private fun generateAccessToken(
        id: String,
        role: String,
        type: String,
        exp: Long,
    ): String =
        Jwts.builder()
            .setSubject(id)
            .setHeaderParam("typ", type)
            .claim("role", role)
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .setExpiration(Date(System.currentTimeMillis() + exp * 1000))
            .setIssuedAt(Date())
            .compact()

    /**
     * 리프레시 토큰을 생성합니다.
     *
     * @param role 사용자 역할
     * @param type 토큰 타입
     * @param exp 만료 시간 (초)
     * @return 생성된 리프레시 토큰
     */
    private fun generateRefreshToken(
        role: String,
        type: String,
        exp: Long,
    ): String =
        Jwts.builder()
            .setHeaderParam("typ", type)
            .claim("role", role)
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .setExpiration(Date(System.currentTimeMillis() + exp * 1000))
            .setIssuedAt(Date())
            .compact()

    /**
     * HTTP 요청에서 토큰을 추출합니다.
     *
     * @param request HTTP 요청 객체
     * @return 추출된 토큰 (없으면 null)
     */
    fun resolveToken(request: HttpServletRequest): String? =
        request.getHeader(jwtProperties.header)?.also {
            if (it.startsWith(jwtProperties.prefix)) {
                return it.substring(jwtProperties.prefix.length)
            }
        }

    /**
     * 토큰으로부터 인증 객체를 생성합니다.
     *
     * @param token JWT 토큰
     * @return Spring Security 인증 객체
     * @throws InvalidTokenException 토큰이 유효하지 않은 경우
     */
    fun authentication(token: String): Authentication? {
        val body: Claims = getJws(token).body
        if (!isRefreshToken(token)) throw InvalidTokenException
        val userDetails: UserDetails = getDetails(body)
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    /**
     * 토큰을 파싱하여 JWS 객체를 반환합니다.
     *
     * @param token 파싱할 JWT 토큰
     * @return 파싱된 JWS 객체
     * @throws ExpiredTokenException 토큰이 만료된 경우
     * @throws InvalidTokenException 토큰이 유효하지 않은 경우
     */
    private fun getJws(token: String): Jws<Claims> {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
        } catch (e: ExpiredJwtException) {
            throw ExpiredTokenException
        } catch (e: Exception) {
            throw InvalidTokenException
        }
    }

    /**
     * 토큰이 리프레시 토큰인지 확인합니다.
     *
     * @param token 확인할 토큰
     * @return 리프레시 토큰 여부
     */
    private fun isRefreshToken(token: String?): Boolean {
        return REFRESH_KEY == getJws(token!!).header["typ"].toString()
    }

    /**
     * 토큰에서 역할 정보를 추출합니다.
     *
     * @param token JWT 토큰
     * @return 사용자 역할
     */
    fun getRole(token: String) = getJws(token).body["role"].toString()

    /**
     * 클레임에서 사용자 상세 정보를 조회합니다.
     *
     * @param body 토큰의 클레임 정보
     * @return 사용자 상세 정보
     */
    private fun getDetails(body: Claims): UserDetails {
        return if (UserRole.USER.toString() == body["role"].toString()) {
            authDetailsService.loadUserByUsername(body.subject)
        } else {
            adminDetailsService.loadUserByUsername(body.subject)
        }
    }
}
