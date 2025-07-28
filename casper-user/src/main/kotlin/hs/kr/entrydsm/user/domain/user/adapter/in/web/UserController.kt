package hs.kr.entrydsm.user.domain.user.adapter.`in`.web

import hs.kr.entrydsm.user.domain.user.adapter.`in`.web.dto.request.ChangePasswordRequest
import hs.kr.entrydsm.user.domain.user.adapter.`in`.web.dto.request.ReactivateRequest
import hs.kr.entrydsm.user.domain.user.adapter.`in`.web.dto.request.UserLoginRequest
import hs.kr.entrydsm.user.domain.user.adapter.`in`.web.dto.request.UserSignupRequest
import hs.kr.entrydsm.user.domain.user.adapter.`in`.web.dto.request.WithdrawalRequest
import hs.kr.entrydsm.user.domain.user.adapter.`in`.web.dto.response.UserResponse
import hs.kr.entrydsm.user.domain.user.application.port.`in`.ChangePasswordUseCase
import hs.kr.entrydsm.user.domain.user.application.port.`in`.QueryUserByUUIDUseCase
import hs.kr.entrydsm.user.domain.user.application.port.`in`.QueryUserInfoUseCase
import hs.kr.entrydsm.user.domain.user.application.port.`in`.UserLoginUseCase
import hs.kr.entrydsm.user.domain.user.application.port.`in`.UserReactivationUseCase
import hs.kr.entrydsm.user.domain.user.application.port.`in`.UserSignupUseCase
import hs.kr.entrydsm.user.domain.user.application.port.`in`.UserTokenRefreshUseCase
import hs.kr.entrydsm.user.domain.user.application.port.`in`.UserWithdrawalUseCase
import hs.kr.entrydsm.user.global.document.user.UserApiDocument
import hs.kr.entrydsm.user.global.utils.token.dto.TokenResponse
import hs.kr.entrydsm.user.infrastructure.grpc.server.dto.InternalUserResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

/**
 * 사용자 관련 REST API 컨트롤러 클래스입니다.
 * 사용자 회원가입, 로그인, 정보 조회, 탈퇴 등의 HTTP 요청을 처리합니다.
 *
 * @property userSignupUseCase 사용자 회원가입 유스케이스
 * @property userLoginUseCase 사용자 로그인 유스케이스
 * @property changePasswordUseCase 비밀번호 변경 유스케이스
 * @property userTokenRefreshUseCase 토큰 갱신 유스케이스
 * @property userWithdrawalUseCase 사용자 탈퇴 유스케이스
 * @property queryUserByUUIDUseCase UUID로 사용자 조회 유스케이스
 * @property queryUserInfoUseCase 사용자 정보 조회 유스케이스
 * @property userReactivationUseCase 사용자 재활성화 유스케이스
 */
@RequestMapping("/user")
@RestController
class UserController(
    private val userSignupUseCase: UserSignupUseCase,
    private val userLoginUseCase: UserLoginUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val userTokenRefreshUseCase: UserTokenRefreshUseCase,
    private val userWithdrawalUseCase: UserWithdrawalUseCase,
    private val queryUserByUUIDUseCase: QueryUserByUUIDUseCase,
    private val queryUserInfoUseCase: QueryUserInfoUseCase,
    private val userReactivationUseCase: UserReactivationUseCase,
) : UserApiDocument {
    /**
     * 사용자 회원가입을 처리합니다.
     * Pass 인증을 통해 검증된 사용자의 회원가입을 진행합니다.
     *
     * @param userSignupRequest 회원가입 요청 정보
     * @return 생성된 JWT 토큰 응답
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    override fun signup(
        @RequestBody @Valid
        userSignupRequest: UserSignupRequest,
    ): TokenResponse = userSignupUseCase.signup(userSignupRequest)

    /**
     * 사용자 로그인을 처리합니다.
     * 전화번호와 비밀번호를 검증하여 JWT 토큰을 발급합니다.
     *
     * @param userLoginRequest 로그인 요청 정보
     * @return 생성된 JWT 토큰 응답
     */
    @PostMapping("/auth")
    override fun login(
        @RequestBody @Valid
        userLoginRequest: UserLoginRequest,
    ): TokenResponse = userLoginUseCase.login(userLoginRequest)

    /**
     * 탈퇴한 계정을 재활성화합니다.
     *
     * @param request 재활성화 요청 정보
     */
    @PostMapping("/reactivate")
    fun reactivateAccount(
        @RequestBody @Valid request: ReactivateRequest,
    ) {
        userReactivationUseCase.reactivateWithdrawnUser(request)
    }

    /**
     * 사용자 비밀번호를 변경합니다.
     * Pass 인증을 통한 본인 확인 후 새로운 비밀번호로 변경합니다.
     *
     * @param changePasswordRequest 비밀번호 변경 요청 정보
     */
    @PatchMapping("/password")
    override fun changePassword(
        @RequestBody @Valid
        changePasswordRequest: ChangePasswordRequest,
    ) = changePasswordUseCase.changePassword(changePasswordRequest)

    /**
     * 만료된 액세스 토큰을 갱신합니다.
     *
     * @param refreshToken 리프레시 토큰
     * @return 새로 발급된 JWT 토큰 응답
     */
    @PutMapping("/auth")
    override fun tokenRefresh(
        @RequestHeader("X-Refresh-Token") refreshToken: String,
    ): TokenResponse = userTokenRefreshUseCase.refresh(refreshToken)

    /**
     * 사용자 탈퇴를 처리합니다.
     * 비밀번호 확인 후 계정을 비활성화합니다.
     *
     * @param request 탈퇴 요청 정보
     */
    @DeleteMapping
    override fun withdrawal(
        @RequestBody @Valid request: WithdrawalRequest,
    ) = userWithdrawalUseCase.withdrawal(request)

    /**
     * UUID로 사용자 정보를 조회합니다.
     * 내부 시스템 간 통신에서 사용되는 API입니다.
     *
     * @param userId 조회할 사용자 UUID
     * @return 내부 시스템용 사용자 정보 응답
     */
    @GetMapping("/{userId}")
    override fun findUserByUUID(
        @PathVariable userId: UUID,
    ): InternalUserResponse = queryUserByUUIDUseCase.getUserById(userId)

    /**
     * 현재 로그인한 사용자의 정보를 조회합니다.
     *
     * @return 사용자 정보 응답
     */
    @GetMapping("/info")
    override fun getUserInfo(): UserResponse = queryUserInfoUseCase.getUserInfo()
}
