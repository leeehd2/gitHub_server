package com.ld.server.domain.model

interface UserRepository {
    fun findByEmailOrNull(email: String): User?
    fun findByNicknameOrNull(nickname: String): User?
    fun save(user: User): User
    fun findById(id: Long): User
}
