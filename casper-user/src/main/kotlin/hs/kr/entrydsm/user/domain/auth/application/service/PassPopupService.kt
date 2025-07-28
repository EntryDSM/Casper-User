package hs.kr.entrydsm.user.domain.auth.application.service

import hs.kr.entrydsm.user.domain.auth.adapter.`in`.web.dto.request.PassPopupRequest
import hs.kr.entrydsm.user.domain.auth.application.port.`in`.PassPopupUseCase
import hs.kr.entrydsm.user.global.exception.InternalServerErrorException
import hs.kr.entrydsm.user.global.utils.pass.RedirectUrlChecker
import kcb.module.v3.OkCert
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 패스 팝업 생성 비즈니스 로직을 처리하는 서비스 클래스입니다.
 */
@Service
class PassPopupService(
    private val redirectUrlChecker: RedirectUrlChecker,
) : PassPopupUseCase {
    companion object {
        private const val TARGET = "PROD"
    }

    @Value("\${pass.site-name}")
    private lateinit var siteName: String

    @Value("\${pass.site-url}")
    private lateinit var siteUrl: String

    @Value("\${pass.popup-url}")
    private lateinit var popupUrl: String

    @Value("\${pass.cp-cd}")
    private lateinit var cpCd: String

    @Value("\${pass.license}")
    private lateinit var license: String

    private val svcName = "IDS_HS_POPUP_START"

    private val rqstCausCd = "00"

    /**
     * 패스 인증 팝업 HTML을 생성합니다.
     */
    @Transactional
    override fun generatePopup(passPopupRequest: PassPopupRequest): String {
        redirectUrlChecker.checkRedirectUrl(passPopupRequest.redirectUrl)
        try {
            val reqJson = JSONObject()
            reqJson.put("RETURN_URL", passPopupRequest.redirectUrl)
            reqJson.put("SITE_NAME", siteName)
            reqJson.put("SITE_URL", siteUrl)
            reqJson.put("RQST_CAUS_CD", rqstCausCd)

            val reqStr: String = reqJson.toString()

            val okcert = OkCert()

            val resultStr: String = okcert.callOkCert(TARGET, cpCd, svcName, license, reqStr)

            val resJson = JSONObject(resultStr)

            val rsltCd: String = resJson.getString("RSLT_CD")
            val rsltMsg: String = resJson.getString("RSLT_MSG")
            var mdlTkn = ""

            var succ = false

            if ("B000" == rsltCd && resJson.has("MDL_TKN")) {
                mdlTkn = resJson.getString("MDL_TKN")
                succ = true
            }

            val htmlBuilder = StringBuilder()
            htmlBuilder.append("<html>")
            htmlBuilder.append("<title>KCB 휴대폰 본인확인 서비스 샘플 2</title>")
            htmlBuilder.append("<head>")
            htmlBuilder.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=euc-kr\">")
            htmlBuilder.append("<script type=\"text/javascript\">")
            htmlBuilder.append("function request(){")
            htmlBuilder.append("document.form1.action = \"$popupUrl\";")
            htmlBuilder.append("document.form1.method = \"post\";")
            htmlBuilder.append("document.form1.submit();")
            htmlBuilder.append("}")
            htmlBuilder.append("</script>")
            htmlBuilder.append("</head>")
            htmlBuilder.append("<body>")
            htmlBuilder.append("<form name=\"form1\">")
            htmlBuilder.append(
                "<input type=\"hidden\" name=\"tc\" value=\"kcb.oknm.online.safehscert.popup.cmd.P931_CertChoiceCmd\"/>",
            )
            htmlBuilder.append("<input type=\"hidden\" name=\"cp_cd\" value=\"$cpCd\"/>")
            htmlBuilder.append("<input type=\"hidden\" name=\"mdl_tkn\" value=\"$mdlTkn\"/>")
            htmlBuilder.append("<input type=\"hidden\" name=\"target_id\" value=\"\"/>")
            htmlBuilder.append("</form>")
            htmlBuilder.append("</body>")
            htmlBuilder.append("<script>")
            if (succ) {
                htmlBuilder.append("request();")
            } else {
                htmlBuilder.append("alert('$rsltCd : $rsltMsg'); self.close();")
            }
            htmlBuilder.append("</script>")
            htmlBuilder.append("</html>")

            return htmlBuilder.toString()
        } catch (e: Exception) {
            println(e.message)
            throw InternalServerErrorException
        }
    }
}
