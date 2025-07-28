package hs.kr.entrydsm.user.global.converter

import hs.kr.entrydsm.user.global.utils.encryption.EncryptionUtil
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import org.springframework.stereotype.Component

/**
 * JPA 엔티티의 문자열 필드를 암호화하여 저장하는 컨버터 클래스입니다.
 * 민감한 개인정보를 데이터베이스에 안전하게 저장하기 위해 사용됩니다.
 *
 * @property encryptionUtil 암호화/복호화를 담당하는 유틸리티
 */
@Component
@Converter
class EncryptedStringConverter(
    private val encryptionUtil: EncryptionUtil,
) : AttributeConverter<String, String> {
    /**
     * 엔티티 속성을 데이터베이스 컬럼으로 변환할 때 암호화를 수행합니다.
     *
     * @param attributes 암호화할 엔티티 속성값
     * @return 암호화된 문자열 (null이면 null 반환)
     */
    override fun convertToDatabaseColumn(attributes: String?): String? {
        return attributes?.let { encryptionUtil.encrypt(it) }
    }

    /**
     * 데이터베이스 컬럼을 엔티티 속성으로 변환할 때 복호화를 수행합니다.
     *
     * @param dbData 복호화할 데이터베이스 값
     * @return 복호화된 문자열 (null이면 null 반환)
     */
    override fun convertToEntityAttribute(dbData: String?): String? {
        return dbData?.let { encryptionUtil.decrypt(it) }
    }
}
