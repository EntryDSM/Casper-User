package hs.kr.entrydsm.user.domain.user.adapter.`in`.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 샘플 API를 제공하는 컨트롤러 클래스입니다.
 */
@RequestMapping("/api/v1/samples")
@RestController
class SampleController {
    /**
     * 샘플 ID로 샘플 정보를 조회합니다.
     */
    @GetMapping("{sampleId}")
    fun getSampleById(
        @PathVariable sampleId: String,
    ): SampleResponse = SampleResponse(sampleId, "sample-$sampleId")
}

/**
 * 샘플 응답 데이터를 담는 클래스입니다.
 */
data class SampleResponse(
    val sampleId: String,
    val name: String,
)
