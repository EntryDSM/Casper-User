package hs.kr.entrydsm.user.domain.user.application.service

import hs.kr.entrydsm.user.domain.user.application.port.`in`.DeleteReceiptCodeUseCase
import hs.kr.entrydsm.user.domain.user.application.port.out.QueryUserPort
import hs.kr.entrydsm.user.domain.user.application.port.out.SaveUserPort
import hs.kr.entrydsm.user.domain.user.exception.UserNotFoundException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class DeleteReceiptCodeService(
    private val queryUserPort: QueryUserPort,
    private val saveUserPort: SaveUserPort,
) : DeleteReceiptCodeUseCase {

    @Transactional
    override fun deleteReceiptCode(receiptCode: Long) {
        val user = queryUserPort.findByReceiptCode(receiptCode)
            ?: throw UserNotFoundException

        val updatedUser = user.copy(receiptCode = null)
        saveUserPort.save(updatedUser)

//        registerAfterCommitCallback(receiptCode)
//        try {
//            val user = queryUserPort.findByReceiptCode(receiptCode)
//
//            if (user == null) {
//                userEventProducer.sendReceiptCodeDeleteFailed(
//                    receiptCode = receiptCode,
//                    reason = "User not found",
//                )
//                throw UserNotFoundException
//            }
//
//            val updatedUser = user.copy(receiptCode = null)
//            saveUserPort.save(updatedUser)
//
//            registerAfterCommitCallback(receiptCode)
//        } catch (e: Exception) {
//            if (e !is UserNotFoundException) {
//                userEventProducer.sendReceiptCodeDeleteFailed(
//                    receiptCode = receiptCode,
//                    reason = e.message ?: "Unknown error",
//                )
//            }
//            throw e // 예외 다시 던져서 롤백 발생
//        }
    }

//    private fun registerAfterCommitCallback(
//        receiptCode: Long
//    ) {
//        val callback =
//            object : TransactionSynchronization {
//                override fun afterCommit() {
//                    userEventProducer.sendReceiptCodeDeleteCompleted(receiptCode)
//                }
//            }
//        TransactionSynchronizationManager.registerSynchronization(callback)
//    }
}