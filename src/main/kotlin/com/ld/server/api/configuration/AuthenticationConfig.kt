package com.ld.server.api.configuration

import com.ld.server.core.util.ApplicationConfigUtils
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.basic
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.configureAuthentication() {
    install(Authentication) {
        //basic("autb-basic"){
            jwt("auth-basic") {
                realm = ApplicationConfigUtils.getConfigProperty("jwt.secret")
            }
        //}
    }
    routing {
        authenticate("auth-basic") {
            get("/secure") {
                call.respondText("You are authenticated!")
            }
        }

        get("/a") {
            call.respondText("Welcome to the public endpoint!")
        }
    }
}
