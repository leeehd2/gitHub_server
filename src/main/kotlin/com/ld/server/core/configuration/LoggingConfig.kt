package com.ld.server.core.configuration

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.application.log
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.request.path
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.event.Level

inline fun <reified T> T.logger(): Logger = LoggerFactory.getLogger(T::class.java)

fun Application.configureLogging() {
    log.info  ("[configureLogging]=========0=================> ")
    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }
}
