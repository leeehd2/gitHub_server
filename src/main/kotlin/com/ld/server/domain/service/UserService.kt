package com.ld.server.domain.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.ld.server.api.dto.SignUpUserRequest
import com.ld.server.api.dto.GetUserResponse
import com.ld.server.api.dto.LoginUserResponse
import com.ld.server.domain.model.User
import com.ld.server.domain.model.UserRepository
import io.ktor.server.plugins.BadRequestException
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class UserService(
    private val jwtSecretKey: String,
    private val userRepository: UserRepository
) {
    fun signUp(
        request: SignUpUserRequest
    ): LoginUserResponse = transaction {
        userRepository.findByEmailOrNull(request.email)?.let {
            throw BadRequestException("duplicate email")
        }
        userRepository.findByNicknameOrNull(request.nickname)?.let {
            throw BadRequestException("duplicate nickname")
        }
        val user = userRepository.save(
            User.create(
                nickname = request.nickname,
                email = request.email,
                encPassword = request.password
            )
        )

        val token = JWT.create()
            .withClaim("userId", user.id)
            .withExpiresAt(Date(System.currentTimeMillis() + 60000))
            .sign(Algorithm.HMAC256(jwtSecretKey))

        return@transaction LoginUserResponse(
            userId = user.id,
            accessToken = token
        )
    }

    fun getUser(id: Long): GetUserResponse {
        val user = userRepository.findById(id)
        return GetUserResponse(user)
    }

    fun updateUser(id: Long, nickname: String): GetUserResponse = transaction {
        return@transaction userRepository.findById(id).apply {
            updateUserInfo(nickname)
        }.let {
            val updatedUser = userRepository.save(it)
            GetUserResponse(updatedUser)
        }
    }
}
