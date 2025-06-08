package hs.kr.entrydsm.user.domain.user.service

import hs.kr.entrydsm.user.domain.auth.domain.repository.PassInfoRepository
import hs.kr.entrydsm.user.domain.auth.exception.PassInfoNotFoundException
import hs.kr.entrydsm.user.domain.user.domain.repository.UserRepository
import hs.kr.entrydsm.user.domain.user.exception.UserNotFoundException
import hs.kr.entrydsm.user.domain.user.presentation.dto.request.ChangePasswordRequest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChangePasswordService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val passInfoRepository: PassInfoRepository,
) {
    @Transactional
    fun execute(changePasswordRequest: ChangePasswordRequest) {
        changePasswordRequest.phoneNumber.takeIf { passInfoRepository.existsByPhoneNumber(it) }
            ?.let { phoneNumber ->
                userRepository.findByPhoneNumber(phoneNumber)
                    ?.changePassword(passwordEncoder.encode(changePasswordRequest.newPassword))
                    ?: throw UserNotFoundException
            } ?: throw PassInfoNotFoundException
    }
}
