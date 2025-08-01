package hs.kr.entrydsm.user.global.utils.encryption

import org.springframework.stereotype.Component
import java.security.MessageDigest

/**
 * 해시 암호화 유틸리티 클래스
 *
 * 문자열을 다양한 해시 알고리즘으로 암호화하는 기능을 제공합니다.
 */
@Component
object HashUtil {
    
    /**
     * 입력 문자열을 SHA-256 해시로 변환합니다.
     *
     * @param input 해시화할 원본 문자열
     * @return SHA-256 해시값을 16진수 문자열로 반환
     */
    fun sha256(input: String): String {
        return MessageDigest.getInstance("SHA-256")
            .digest(input.toByteArray())
            .joinToString(""){ "%02x".format(it) }
    }
}