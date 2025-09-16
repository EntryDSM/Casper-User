package hs.kr.entrydsm.user.global.document.user

import hs.kr.entrydsm.user.domain.user.adapter.`in`.web.dto.request.ChangePasswordRequest
import hs.kr.entrydsm.user.domain.user.adapter.`in`.web.dto.request.UserLoginRequest
import hs.kr.entrydsm.user.domain.user.adapter.`in`.web.dto.request.UserSignupRequest
import hs.kr.entrydsm.user.domain.user.adapter.`in`.web.dto.request.WithdrawalRequest
import hs.kr.entrydsm.user.domain.user.adapter.`in`.web.dto.response.UserResponse
import hs.kr.entrydsm.user.global.utils.token.dto.TokenResponse
import hs.kr.entrydsm.user.infrastructure.grpc.server.dto.InternalUserResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.UUID

/**
 * 사용자 API 문서화를 위한 인터페이스입니다.
 */
@Tag(name = "User", description = "사용자 API")
interface UserApiDocument {
    @Operation(
        summary = "사용자 회원가입",
        description = "PASS 인증 완료 후 Redis에 저장된 인증 정보를 사용하여 회원가입을 진행합니다. 성공 시 JWT 토큰과 UserInfo를 Redis에 저장합니다.",
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "201",
            description = "회원가입 성공",
            content = arrayOf(Content(schema = Schema(implementation = TokenResponse::class))),
        ),
        ApiResponse(
            responseCode = "409",
            description = "이미 존재하는 전화번호 - User Already Exists",
            content = arrayOf(Content()),
        ),
        ApiResponse(
            responseCode = "404",
            description = "Redis에서 PASS 인증 정보를 찾을 수 없음 - Pass Info Not Found",
            content = arrayOf(Content()),
        ),
    )
    /**
     * 사용자 회원가입을 처리합니다.
     */
    @ResponseStatus(HttpStatus.CREATED)
    fun signup(
        @RequestBody @Valid userSignupRequest: UserSignupRequest,
    ): TokenResponse

    /**
     * 사용자 로그인을 처리합니다.
     */
    @Operation(
        summary = "사용자 로그인",
        description = "전화번호와 비밀번호로 로그인하여 JWT 토큰을 발급받습니다. 성공 시 UserInfo도 Redis에 저장됩니다.",
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "로그인 성공",
            content = arrayOf(Content(schema = Schema(implementation = TokenResponse::class))),
        ),
        ApiResponse(
            responseCode = "401",
            description = "비밀번호가 일치하지 않음 - Invalid User Password",
            content = arrayOf(Content()),
        ),
        ApiResponse(
            responseCode = "404",
            description = "존재하지 않는 전화번호 - User Not Found",
            content = arrayOf(Content()),
        ),
    )
    fun login(
        @RequestBody @Valid userLoginRequest: UserLoginRequest,
    ): TokenResponse

    /**
     * 사용자 비밀번호를 변경합니다.
     */
    @Operation(
        summary = "비밀번호 변경",
        description = "전화번호로 사용자를 찾아 비밀번호를 변경합니다.",
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "204",
            description = "비밀번호 변경 성공",
            content = arrayOf(Content()),
        ),
        ApiResponse(
            responseCode = "404",
            description = "존재하지 않는 전화번호 - User Not Found",
            content = arrayOf(Content()),
        ),
    )
    fun changePassword(
        @RequestBody @Valid changePasswordRequest: ChangePasswordRequest,
    )

    /**
     * 사용자 토큰을 갱신합니다.
     */
    @Operation(
        summary = "토큰 갱신",
        description = "Refresh Token으로 새로운 Access Token을 발급받습니다.",
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "토큰 갱신 성공",
            content = arrayOf(Content(schema = Schema(implementation = TokenResponse::class))),
        ),
        ApiResponse(
            responseCode = "401",
            description = "유효하지 않거나 만료된 토큰 - Invalid Token | Expired Token",
            content = arrayOf(Content()),
        ),
    )
    fun tokenRefresh(
        @Parameter(description = "갱신할 Refresh Token", required = true)
        @RequestHeader("X-Refresh-Token") refreshToken: String,
    ): TokenResponse

    @Operation(
        summary = "회원탈퇴",
        description = "현재 로그인한 사용자의 계정을 삭제합니다.",
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "204",
            description = "탈퇴 완료",
            content = arrayOf(Content()),
        ),
        ApiResponse(
            responseCode = "401",
            description = "인증되지 않은 사용자 - Invalid Token | Expired Token",
            content = arrayOf(Content()),
        ),
        ApiResponse(
            responseCode = "404",
            description = "사용자를 찾을 수 없음 - User Not Found",
            content = arrayOf(Content()),
        ),
    )
    /**
     * 사용자 탈퇴를 처리합니다.
     */
    @SecurityRequirement(name = "bearerAuth")
    fun withdrawal(
        @RequestBody @Valid request: WithdrawalRequest,
    )

    /**
     * UUID로 사용자 정보를 조회합니다.
     */
    @Operation(
        summary = "사용자 조회 (내부용)",
        description = "UUID로 사용자 정보를 조회합니다. 내부 시스템 간 통신용 API입니다.",
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "조회 성공",
            content = arrayOf(Content(schema = Schema(implementation = InternalUserResponse::class))),
        ),
        ApiResponse(
            responseCode = "404",
            description = "사용자를 찾을 수 없음 - User Not Found",
            content = arrayOf(Content()),
        ),
    )
    fun findUserByUUID(
        @Parameter(description = "조회할 사용자의 UUID", required = true)
        @PathVariable userId: UUID,
    ): InternalUserResponse

    @Operation(
        summary = "내 정보 조회",
        description = "현재 로그인한 사용자의 정보를 조회합니다.",
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "조회 성공",
            content = arrayOf(Content(schema = Schema(implementation = UserResponse::class))),
        ),
        ApiResponse(
            responseCode = "401",
            description = "인증되지 않은 사용자 - Invalid Token | Expired Token",
            content = arrayOf(Content()),
        ),
        ApiResponse(
            responseCode = "404",
            description = "사용자를 찾을 수 없음 - User Not Found",
            content = arrayOf(Content()),
        ),
    )
    /**
     * 현재 로그인한 사용자의 정보를 조회합니다.
     */
    @SecurityRequirement(name = "bearerAuth")
    fun getUserInfo(): UserResponse
}
