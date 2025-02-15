package com.ld.server.api.dto

import com.ld.server.domain.model.User

data class UserSignUpRequest(
    val memName: String,
    val password: String,
    val email: String
) {
    init {
        // TODO: add email, password format validation
    }
}


data class SignUpUserRequest(
    val nickname: String,
    val email: String,
    val password: String
) {
    init {
        // TODO: add email, password format validation
    }
}

data class GetUserResponse(
    val id: Long,
    val nickname: String,
    val email: String,
    val encPassword: String,
    val createdAt: String,
    val updatedAt: String
) {
    constructor(entity: User) : this(
        id = entity.id,
        nickname = entity.nickname,
        email = entity.email,
        encPassword = entity.encPassword,
        createdAt = entity.createdAt.toString(),
        updatedAt = entity.updatedAt.toString()
    )
}

data class UpdateUserInfoRequest(
    val nickname: String
)

data class LoginUserResponse(
    val userId: Long,
    val accessToken: String
)

data class UserSignUpResponse(
    val userId: Long,
    val accessToken: String
)
