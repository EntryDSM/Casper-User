package hs.kr.entrydsm.user.global.utils.pass

import hs.kr.entrydsm.user.domain.auth.exception.InvalidOkCertConnectException
import kcb.module.v3.OkCert
import kcb.module.v3.exception.OkCertException
import kcb.org.json.JSONObject
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

/**
 * KCB 패스 인증 유틸리티 클래스입니다.
 */
@Component
class PassUtil {
    companion object {
        /**
         * KCB OkCert 인스턴스
         */
        private val okCert = OkCert()

        /**
         * KCB 인증 대상 환경 (운영환경)
         */
        private val TARGET = "PROD"

        /**
         * KCB 서비스명
         */
        private val SVC_NAME = "IDS_HS_POPUP_RESULT"

        /**
         * 모델 토큰 키명
         */
        private val MODEL_TOKEN = "MDL_TKN"
    }

    /**
     * KCB CP 코드
     */
    @Value("\${pass.cp-cd}")
    private lateinit var cpCd: String

    /**
     * KCB 라이선스 키
     */
    @Value("\${pass.license}")
    private lateinit var license: String

    /**
     * 패스 인증 토큰으로부터 응답 JSON을 가져옵니다.
     */
    fun getResponseJson(token: String?): JSONObject? {
        val reqJson = JSONObject()
        reqJson.put(MODEL_TOKEN, token)
        val reqStr = reqJson.toString()
        val resultStr: String? =
            try {
                okCert.callOkCert(TARGET, cpCd, SVC_NAME, license, reqStr)
            } catch (e: OkCertException) {
                throw InvalidOkCertConnectException
            }
        assert(resultStr != null)
        return JSONObject(resultStr)
    }
}
