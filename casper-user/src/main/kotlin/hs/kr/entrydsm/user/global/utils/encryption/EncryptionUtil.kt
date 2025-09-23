package hs.kr.entrydsm.user.global.utils.encryption

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.SecureRandom
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * AES 암호화/복호화를 담당하는 유틸리티 클래스입니다.
 * 민감한 개인정보를 안전하게 저장하기 위한 암호화 기능을 제공합니다.
 *
 * @property secretKey 암호화에 사용할 비밀키
 */
@Component
class EncryptionUtil(
    @Value("\${app.encryption.key}")
    val secretKey: String,
) {
    companion object {
        private const val ALGORITHM = "AES"
        private const val TRANSFORMATION = "AES/CBC/PKCS5Padding"
        private const val IV_SIZE = 16
    }

    /**
     * 평문을 AES 알고리즘으로 암호화합니다.
     *
     * @param plainText 암호화할 평문
     * @return Base64로 인코딩된 암호화된 문자열
     */
    fun encrypt(plainText: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        // 키를 16바이트로 제한
        val keyBytes = secretKey.toByteArray().take(16).toByteArray()
        val keySpec = SecretKeySpec(keyBytes, ALGORITHM)

        val iv = ByteArray(IV_SIZE)
        SecureRandom().nextBytes(iv)
        val ivSpec = IvParameterSpec(iv)

        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
        val encryptedBytes = cipher.doFinal(plainText.toByteArray())

        val combined = iv + encryptedBytes
        return Base64.getEncoder().encodeToString(combined)
    }

    /**
     * 암호화된 텍스트를 복호화하여 평문으로 변환합니다.
     *
     * @param encryptedText 복호화할 암호화된 텍스트
     * @return 복호화된 평문
     */
    fun decrypt(encryptedText: String): String {
        val combined = Base64.getDecoder().decode(encryptedText)

        val iv = combined.sliceArray(0 until IV_SIZE)
        val encryptedBytes = combined.sliceArray(IV_SIZE until combined.size)

        val cipher = Cipher.getInstance(TRANSFORMATION)
        // 키를 16바이트로 제한
        val keyBytes = secretKey.toByteArray().take(16).toByteArray()
        val keySpec = SecretKeySpec(keyBytes, ALGORITHM)
        val ivSpec = IvParameterSpec(iv)

        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
        val decryptedBytes = cipher.doFinal(encryptedBytes)
        return String(decryptedBytes)
    }
}
