package com.ld.server.api.router

import io.ktor.server.routing.route
import kotlinx.serialization.Serializable
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.ld.server.domain.model.User
import com.ld.server.work.DatabaseCon
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import java.sql.Connection
import java.sql.ResultSet

    fun Route.UserSearch(){
        post("/users") {
            val user = call.receive<user>()
            val objectMapper = ObjectMapper()
                val Dataconn: DatabaseCon = DatabaseCon()
                Dataconn.conn()
                val conn: Connection = Dataconn.conn!!

                objectMapper.enable(SerializationFeature.INDENT_OUTPUT)

                val personList = mutableListOf<Person1>()

                println("test=================")
                // 데이터 조회
                // 데이터 조회
                var statement = conn.createStatement()
                var resultSet: ResultSet = statement.executeQuery("SELECT user_id FROM user where user_id = ${user.userid} and enc_password = ${user.password}   limit 1")


                var jsonString: String = ""
                while (resultSet.next()) {
                    //  println("product_ID: ${resultSet.getInt("product_id")}, product_Name: ${resultSet.getString("product_name")}")
                    // 샘플 객체
                    val user = Person1(resultSet.getString("user_id"), resultSet.getString("enc_password"))

// 객체를 JSON 문자열로 변환
                    jsonString = objectMapper.writeValueAsString(user)

                    call.respondText(jsonString)

// 출력
                    // println("=============>${jsonString}")

                }

                // 리소스 정리
                resultSet.close()
                statement.close()
                conn.close()



            }
    }




@Serializable
internal data class user // 생성자, getter, setter
    (var userid: String, var password: String)







