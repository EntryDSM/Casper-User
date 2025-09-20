package hs.kr.entrydsm.user.domain.auth.adapter.`in`.web

import hs.kr.entrydsm.user.domain.auth.adapter.`in`.web.dto.request.PassPopupRequest
import hs.kr.entrydsm.user.domain.auth.adapter.`in`.web.dto.resopnse.QueryPassInfoResponse
import hs.kr.entrydsm.user.domain.auth.application.port.`in`.PassPopupUseCase
import hs.kr.entrydsm.user.domain.auth.application.port.`in`.QueryPassInfoUseCase
import hs.kr.entrydsm.user.global.document.auth.AuthApiDocument
import jakarta.validation.Valid
import org.springframework.core.io.ClassPathResource
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * 패스 인증 관련 HTTP 요청을 처리하는 REST 컨트롤러 클래스입니다.
 */
@RestController
@RequestMapping("/user/verify")
class PassInfoController(
    private val passPopupUseCase: PassPopupUseCase,
    private val queryPassInfoUseCase: QueryPassInfoUseCase,
) : AuthApiDocument {
    /**
     * 패스 인증 정보를 조회합니다.
     */
    @GetMapping("/info")
    override fun getPassInfo(
        @RequestParam("mdl_tkn") token: String,
    ): QueryPassInfoResponse = queryPassInfoUseCase.queryPassInfo(token)

    /**
     * 패스 인증 팝업을 생성합니다.
     */
    @PostMapping("/popup")
    override fun popupPass(
        @RequestBody request: @Valid PassPopupRequest,
    ): String = passPopupUseCase.generatePopup(request)

    /**
     * 패스 callback 화면을 반환합니다.
     * */
    @GetMapping("/callback")
    fun passCallback(): String {
        return ClassPathResource("templates/pass-callback.html")
            .inputStream.bufferedReader().use { it.readText() }
    }
}
