package hs.kr.entrydsm.user.domain.user.application.service

import hs.kr.entrydsm.user.domain.auth.adapter.out.repository.PassInfoRepository
import hs.kr.entrydsm.user.domain.auth.exception.PassInfoNotFoundException
import hs.kr.entrydsm.user.domain.user.adapter.`in`.web.dto.request.UserSignupRequest
import hs.kr.entrydsm.user.domain.user.adapter.out.domain.UserRole
import hs.kr.entrydsm.user.domain.user.application.port.`in`.UserSignupUseCase
import hs.kr.entrydsm.user.domain.user.application.port.out.QueryUserPort
import hs.kr.entrydsm.user.domain.user.application.port.out.SaveUserPort
import hs.kr.entrydsm.user.domain.user.exception.UserAlreadyExistsException
import hs.kr.entrydsm.user.domain.user.model.User
import hs.kr.entrydsm.user.global.security.jwt.JwtTokenProvider
import hs.kr.entrydsm.user.global.utils.encryption.EncryptionUtil
import hs.kr.entrydsm.user.global.utils.encryption.HashUtil
import hs.kr.entrydsm.user.global.utils.token.dto.TokenResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 사용자 회원가입 서비스 클래스입니다.
 * Pass 인증을 통해 검증된 사용자의 회원가입 처리를 담당합니다.
 *
 * @property saveUserPort 사용자 저장 포트
 * @property queryUserPort 사용자 조회 포트
 * @property passInfoRepository Pass 정보 저장소
 * @property passwordEncoder 비밀번호 암호화 인코더
 * @property tokenProvider JWT 토큰 제공자
 * @property encryptionUtil 암호화 유틸리티
 */
@Service
class UserSignupService(
    private val saveUserPort: SaveUserPort,
    private val queryUserPort: QueryUserPort,
    private val passInfoRepository: PassInfoRepository,
    private val passwordEncoder: PasswordEncoder,
    private val tokenProvider: JwtTokenProvider,
    private val encryptionUtil: EncryptionUtil,
) : UserSignupUseCase {
    /**
     * 사용자 회원가입을 처리하고 JWT 토큰을 반환합니다.
     * Pass 인증 정보를 기반으로 사용자를 생성하고 토큰을 발급합니다.
     *
     * @param request 회원가입 요청 정보
     * @return 생성된 JWT 토큰 응답
     * @throws UserAlreadyExistsException 이미 존재하는 사용자인 경우
     * @throws PassInfoNotFoundException Pass 인증 정보를 찾을 수 없는 경우
     */
    @Transactional
    override fun signup(request: UserSignupRequest): TokenResponse {
        val phoneNumber = request.phoneNumber
        val phoneNumberHash = HashUtil.sha256(phoneNumber)

        if (queryUserPort.existsByPhoneNumber(phoneNumber)) {
            throw UserAlreadyExistsException
        }

        val passInfo =
            passInfoRepository.findById(phoneNumberHash)
                .orElseThrow { PassInfoNotFoundException }

        val user =
            User(
                id = null,
                phoneNumber = encryptionUtil.decrypt(passInfo.phoneNumber),
                phoneNumberHash = phoneNumberHash,
                password = passwordEncoder.encode(request.password),
                name = encryptionUtil.decrypt(passInfo.name),
                isParent = request.isParent,
                receiptCode = null,
                role = UserRole.USER,
            )

        val savedUser = saveUserPort.save(user)

        return tokenProvider.generateToken(
            savedUser.id.toString(),
            savedUser.role.toString(),
        )
    }
}
