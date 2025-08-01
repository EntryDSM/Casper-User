package hs.kr.entrydsm.user.domain.user.application.service

import hs.kr.entrydsm.user.domain.auth.adapter.out.repository.PassInfoRepository
import hs.kr.entrydsm.user.domain.auth.exception.PassInfoNotFoundException
import hs.kr.entrydsm.user.domain.user.adapter.`in`.web.dto.request.ChangePasswordRequest
import hs.kr.entrydsm.user.domain.user.application.port.`in`.ChangePasswordUseCase
import hs.kr.entrydsm.user.domain.user.application.port.out.QueryUserPort
import hs.kr.entrydsm.user.domain.user.application.port.out.SaveUserPort
import hs.kr.entrydsm.user.domain.user.exception.UserNotFoundException
import hs.kr.entrydsm.user.global.utils.encryption.HashUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 사용자 비밀번호 변경 서비스 클래스입니다.
 * Pass 인증을 통한 본인 확인 후 비밀번호 변경을 처리합니다.
 *
 * @property queryUserPort 사용자 조회 포트
 * @property saveUserPort 사용자 저장 포트
 * @property passwordEncoder 비밀번호 암호화 인코더
 * @property passInfoRepository Pass 정보 저장소
 */
@Service
class ChangePasswordService(
    private val queryUserPort: QueryUserPort,
    private val saveUserPort: SaveUserPort,
    private val passwordEncoder: PasswordEncoder,
    private val passInfoRepository: PassInfoRepository,
) : ChangePasswordUseCase {
    /**
     * 사용자의 비밀번호를 변경합니다.
     * Pass 인증 확인 후 새로운 비밀번호로 암호화하여 저장합니다.
     *
     * @param request 비밀번호 변경 요청 정보
     * @throws PassInfoNotFoundException Pass 인증 정보가 없는 경우
     * @throws UserNotFoundException 사용자가 존재하지 않는 경우
     */
    @Transactional
    override fun changePassword(request: ChangePasswordRequest) {
        val phoneNumberHash = HashUtil.sha256(request.phoneNumber)

        if (!passInfoRepository.existsById(phoneNumberHash)) {
            throw PassInfoNotFoundException
        }

        val user =
            queryUserPort.findByPhoneNumber(request.phoneNumber)
                ?: throw UserNotFoundException

        val updatedUser = user.changePassword(passwordEncoder.encode(request.newPassword))
        saveUserPort.save(updatedUser)
    }
}
