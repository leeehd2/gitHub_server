package com.ld.server.core.configuration

import com.ld.server.core.util.ApplicationConfigUtils
import com.ld.server.domain.model.ProductRepository
import com.ld.server.domain.model.ProductRepositoryImpl
import com.ld.server.domain.model.UserRepository
import com.ld.server.domain.service.ProductService
import com.ld.server.domain.service.UserService
import com.ld.server.infrastructure.implementation.UserRepositoryExposedImpl
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.SLF4JLogger

val dependencyInjectionModule = module {
    single {
        UserService(
            userRepository = get(),
            jwtSecretKey = ApplicationConfigUtils.getConfigProperty("jwt.secret")
        )
    }
    single<UserRepository> { UserRepositoryExposedImpl() }
    single { ProductService(get()) }
    single<ProductRepository> { ProductRepositoryImpl() }
}

fun Application.configureDependencyInjection() {
    install(Koin) {
        SLF4JLogger()
        modules(dependencyInjectionModule)
    }
}
