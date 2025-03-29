package com.pjt.brandpricing.application.domain.exception

import org.springframework.http.HttpStatus

open class ApplicationException(
    val status: HttpStatus,
    val code: String,
    override val message: String,
    val property: Map<String, Any>? = null
) : RuntimeException() {

    companion object {
        fun ofBadRequest(errorCode: ErrorCode): Throwable = ofBadRequest(errorCode.code, errorCode.message)

        private fun ofBadRequest(errorCode: String, errorMessage: String): Throwable =
            ApplicationException(HttpStatus.BAD_REQUEST, errorCode, errorMessage)

        fun ofNotFound(errorCode: ErrorCode): Throwable = ofNotFound(errorCode.code, errorCode.message)

        private fun ofNotFound(errorCode: String, errorMessage: String): Throwable =
            ApplicationException(HttpStatus.NOT_FOUND, errorCode, errorMessage)
    }
}
