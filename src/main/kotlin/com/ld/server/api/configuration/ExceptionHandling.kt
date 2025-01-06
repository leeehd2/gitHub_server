package com.ld.server.api.configuration

import com.ld.server.core.configuration.logger
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
//import kotlinx.coroutines.internal.ExceptionSuccessfullyProcessed.cause

data class ErrorResponse(
    val errorCode: String = "UNKNOWN_ERROR",
    val message: String? = null
) {
    companion object {
        fun ofNotFound() = ErrorResponse(
            "NOT_FOUND",
            "해당 데이터를 찾을 수 없어요"
        )

        fun ofUnexpectedError() = ErrorResponse(
            message = "예상치 못한 서버 에러가 발생했습니다"
        )
    }
}

fun Application.configureExceptionHandling() {
    val log = logger()
    log.info  ("[configureExceptionHandling]=========0=========${StatusPages.toString()}========> ")
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            log.warn("[Global Ex] $cause")
            log.info("[Global Ex]===========================> $cause")
            call.respond(HttpStatusCode.BadRequest, ErrorResponse(message = cause.localizedMessage))
        }

        exception<NoSuchElementException> { call, _ ->
            call.respond(
                HttpStatusCode.NotFound,
                ErrorResponse.ofNotFound()
            )
        }
    }
}
