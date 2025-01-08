package com.ld.server.api.router

import com.ld.server.api.dto.GetUserResponse
import com.ld.server.api.dto.LoginUserResponse
import com.ld.server.api.dto.SignUpUserRequest
import com.ld.server.api.dto.UpdateUserInfoRequest
import com.ld.server.api.util.RequestUtils.getEntityId
import com.ld.server.api.util.SwaggerUtils.internalServerError
import com.ld.server.api.util.SwaggerUtils.notFound
import com.ld.server.domain.model.User
import com.ld.server.domain.service.UserService
import io.github.smiley4.ktorswaggerui.dsl.routing.get
import io.github.smiley4.ktorswaggerui.dsl.routing.post
import io.github.smiley4.ktorswaggerui.dsl.routing.put
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject

val userTags = listOf("User")



fun Route.userRouter() {
    val userService: UserService by inject()
    println("userTags=====$userService")

    // POST 요청 처리
    post("/users") {

        val user = call.receive<User>()
       // val createdUser = createUser(user)
       // call.respond(HttpStatusCode.Created, createdUser)
    }


    post("/users", {
        println("users=====")
        tags = userTags
        description = "create user"
        request {
            body<SignUpUserRequest>()
        }
        response {
            HttpStatusCode.OK to {
                body<LoginUserResponse>()
            }
            HttpStatusCode.InternalServerError to internalServerError()
        }
    }) {
        val requestBody = call.receive<SignUpUserRequest>()
        val response = userService.signUp(requestBody)
        call.respond(response)
    }

    get("/users/{id}", {
        tags = userTags
        description = "get user"
        println("users/id=====")
        request {
            pathParameter<Long>("id")
        }
        response {
            HttpStatusCode.OK to {
                body<GetUserResponse>()
            }
            HttpStatusCode.NotFound to notFound()
            HttpStatusCode.InternalServerError to internalServerError()
        }
    }) {
        val userId = call.getEntityId()
        call.respond(userService.getUser(userId))
    }

    put("/users/{id}", {
        tags = userTags
        description = "update user"
        request {
            pathParameter<Long>("id")
            body<UpdateUserInfoRequest>()
        }
        response {
            HttpStatusCode.OK to {
                body<GetUserResponse>()
            }
            HttpStatusCode.NotFound to notFound()
            HttpStatusCode.InternalServerError to internalServerError()
        }
    }) {
        val userId = call.getEntityId()
        val request = call.receive<UpdateUserInfoRequest>()
        call.respond(userService.updateUser(userId, request.nickname))
    }
}
