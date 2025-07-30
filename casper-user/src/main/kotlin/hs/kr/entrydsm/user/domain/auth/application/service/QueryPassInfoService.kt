package hs.kr.entrydsm.user.domain.auth.application.service

import hs.kr.entrydsm.user.domain.auth.adapter.`in`.web.dto.resopnse.QueryPassInfoResponse
import hs.kr.entrydsm.user.domain.auth.adapter.out.PassInfo
import hs.kr.entrydsm.user.domain.auth.adapter.out.repository.PassInfoRepository
import hs.kr.entrydsm.user.domain.auth.application.port.`in`.QueryPassInfoUseCase
import hs.kr.entrydsm.user.domain.auth.exception.InvalidPassException
import hs.kr.entrydsm.user.global.utils.encryption.EncryptionUtil
import hs.kr.entrydsm.user.global.utils.pass.PassUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 패스 인증 정보 조회 비즈니스 로직을 처리하는 서비스 클래스입니다.
 */
@Service
class QueryPassInfoService(
    private val passInfoRepository: PassInfoRepository,
    private val passUtil: PassUtil,
    private val encryptionUtil: EncryptionUtil,
) : QueryPassInfoUseCase {
    companion object {
        private val RESULT_CODE = "RSLT_CD"

        private val RESULT_NAME = "RSLT_NAME"

        private val TEL_NO = "TEL_NO"

        private val RESULT_CODE_OK = "B000"
    }

    @Value("\${pass.exp}")
    private var exp: Long = 0L

    /**
     * 토큰을 검증하고 패스 인증 정보를 조회합니다.
     */
    @Transactional
    override fun queryPassInfo(token: String?): QueryPassInfoResponse {
        val resJson = passUtil.getResponseJson(token)
        val resultCode = resJson!!.getString(RESULT_CODE)
        if (RESULT_CODE_OK != resultCode) {
            throw InvalidPassException
        }
        val name = resJson.getString(RESULT_NAME)
        val phoneNumber = resJson.getString(TEL_NO)

        val passInfo =
            PassInfo(
                HashUtil.sha256(phoneNumber),
                encryptionUtil.encrypt(phoneNumber),
                encryptionUtil.encrypt(name),
                exp
            )

        passInfoRepository.save(passInfo)
        return QueryPassInfoResponse(phoneNumber, name)
    }
}
