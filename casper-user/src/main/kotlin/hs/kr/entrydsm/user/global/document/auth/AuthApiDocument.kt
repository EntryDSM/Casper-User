package hs.kr.entrydsm.user.global.document.auth

import hs.kr.entrydsm.user.domain.auth.adapter.`in`.web.dto.request.PassPopupRequest
import hs.kr.entrydsm.user.domain.auth.adapter.`in`.web.dto.resopnse.QueryPassInfoResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

/**
 * 인증 API 문서화를 위한 인터페이스입니다.
 */
@Tag(name = "Auth", description = "PASS 본인인증 API")
interface AuthApiDocument {
    /**
     * PASS 인증 결과를 조회합니다.
     */
    @Operation(
        summary = "PASS 인증 결과 조회",
        description = "KCB PASS 인증 완료 후 모달 토큰으로 인증된 사용자 정보를 조회하고 Redis에 저장합니다.",
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "인증 정보 조회 성공 (Redis에 저장됨)",
            content = arrayOf(Content(schema = Schema(implementation = QueryPassInfoResponse::class))),
        ),
        ApiResponse(
            responseCode = "401",
            description = "PASS 인증 결과 코드가 'B000'이 아님 - Invalid Pass",
            content = arrayOf(Content()),
        ),
        ApiResponse(
            responseCode = "500",
            description = "KCB OkCert 연동 오류 - Invalid OkCert Connection",
            content = arrayOf(Content()),
        ),
    )
    fun getPassInfo(
        @Parameter(description = "KCB에서 발급받은 모달 토큰", required = true)
        @RequestParam("mdl_tkn") token: String,
    ): QueryPassInfoResponse

    /**
     * PASS 팝업 인증을 시작합니다.
     */
    @Operation(
        summary = "PASS 팝업 인증 시작",
        description = "KCB PASS 팝업 인증을 시작하기 위한 HTML 폼을 생성합니다. 리다이렉트 URL 검증 후 KCB API 호출합니다.",
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "KCB 팝업으로 리다이렉트하는 HTML 폼 생성 성공",
            content =
                arrayOf(
                    Content(
                        mediaType = "text/html",
                        schema = Schema(type = "string", description = "자동 submit되는 HTML 폼"),
                    ),
                ),
        ),
        ApiResponse(
            responseCode = "401",
            description = "허용되지 않은 리다이렉트 URL - Invalid Url",
            content = arrayOf(Content()),
        ),
        ApiResponse(
            responseCode = "500",
            description = "KCB API 호출 실패 또는 기타 서버 오류 - Internal Server Error",
            content = arrayOf(Content()),
        ),
    )
    fun popupPass(
        @RequestBody @Valid request: PassPopupRequest,
    ): String
}
