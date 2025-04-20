package hs.kr.entrydsm

/**
 * 테스트 클래스
 * 
 * 주석 강제화를 확인하기 위한 테스트 클래스입니다.
 */
class TestFile {
    /**
     * 주석이 있는 함수
     * 
     * 이 함수는 주석이 있으므로 detekt에서 경고가 발생하지 않습니다.
     */
    fun functionWithComment() {
        println("이 함수는 주석이 있습니다.")
    }
    
    // 이 함수는 KDoc 주석이 없으므로 detekt에서 경고가 발생합니다.
    fun functionWithoutComment() {
        println("이 함수는 주석이 없습니다.")
    }
    
    /**
     * Private 함수에도 주석 필요
     */
    private fun privateFunction() {
        println("Private 함수도 주석이 필요합니다.")
    }
}