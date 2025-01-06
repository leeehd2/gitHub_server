package com.ld.server.api.router

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import io.github.smiley4.ktorswaggerui.dsl.routing.get
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route

fun Route.systemRouter() {
    val objectMapper = ObjectMapper()
// SerializationFeature.INDENT_OUTPUT 활성화
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT)
// 샘플 객체
    val person = Person("홍길동", 30)
// 객체를 JSON 문자열로 변환
    val jsonString = objectMapper.writeValueAsString(person)
// 출력
    println("fffffff=============>"+jsonString)

    get("/health", {
        hidden = true
    }) {
        call.respond("OK")
    }


    get("/ld", {
        hidden = true
    }) {
        call.respond(jsonString)
    }
}









internal class Person // 생성자, getter, setter
    (var name: String, var age: Int)
