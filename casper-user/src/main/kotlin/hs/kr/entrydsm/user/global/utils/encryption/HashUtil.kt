package hs.kr.entrydsm.user.global.utils.encryption

import org.springframework.stereotype.Component
import java.security.MessageDigest

@Component
object HashUtil {
    fun sha256(input: String): String {
        return MessageDigest.getInstance("SHA-256")
            .digest(input.toByteArray())
            .joinToString(""){ "%02x".format(it) }
    }
}