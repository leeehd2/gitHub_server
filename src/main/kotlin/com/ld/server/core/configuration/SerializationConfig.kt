package com.ld.server.core.configuration

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.serialization.jackson.jackson
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.application.log
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


/*
fun Application.configureSerialization() {
    log.info  ("[configureSerialization]=========0=================> ")
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }
}

*/
fun Application.configureSerialization() {

    log.info  ("[configureSerialization]=========0=================> ")
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true // JSON을 보기 좋게 포맷팅
                isLenient = true // 유연한 파싱 허용
                ignoreUnknownKeys = true // 정의되지 않은 키 무시
            }
        )
    }
}

fun Application.module() {
    configureSerialization()
    log.info("[configureSerialization]========11================> ")
    routing {
        post("/ld") {
            val receivedData = call.receive<SampleData>()
            call.respond(mapOf("status" to "success", "received" to receivedData))
        }
    }
    /*
    routing {
        get("/") {
            call.respond(SampleData(id = 1, name = "Kotlin Serialization Example"))
        }
    } */
}

@Serializable
data class SampleData(val id: Int, val name: String)