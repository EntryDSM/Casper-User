package hs.kr.entrydsm.user.global.security.jwt

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * JWT 토큰 관련 설정 프로퍼티 클래스입니다.
 * application.yml에서 auth.jwt 하위 설정값들을 바인딩합니다.
 *
 * @property secretKey JWT 서명에 사용할 비밀키
 * @property accessExp 액세스 토큰 만료 시간 (초)
 * @property refreshExp 리프레시 토큰 만료 시간 (초)
 * @property header JWT 토큰이 포함될 HTTP 헤더명
 * @property prefix JWT 토큰 앞에 붙을 접두사
 */
@ConfigurationProperties("auth.jwt")
class JwtProperties(
    val secretKey: String,
    val accessExp: Long,
    val refreshExp: Long,
    val header: String,
    val prefix: String,
)
