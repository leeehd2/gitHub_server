package com.ld.server.api.router

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.ld.server.api.dto.GetProductRequest
import com.ld.server.api.util.RequestUtils.getQueryParams
import com.ld.server.work.DatabaseCon
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import kotlinx.serialization.Serializable
import java.sql.Connection
import java.sql.ResultSet

    fun Route.UserSearch(){
        get("/users11") {
            println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@22")
            val userid=call.parameters["userid"]
            val password=call.parameters["password"]
            //val user = call.receive<user>()
            //val params = call.getQueryParams<user>()
           // call.receive<>()
            println("1")
            val objectMapper = ObjectMapper()
            println("2")
            val Dataconn: DatabaseCon = DatabaseCon()
                Dataconn.conn()
                val conn: Connection = Dataconn.conn!!

                objectMapper.enable(SerializationFeature.INDENT_OUTPUT)
            println("4")
                val personList = mutableListOf<Person1>()

                println("${userid}=========================${password}")
                // 데이터 조회
                // 데이터 조회

                var statement = conn.createStatement()
                var resultSet: ResultSet = statement.executeQuery("SELECT mem_id , mem_name FROM dongdb.member_tb where mem_id = '${userid}' and password = '${password}' ")

                var jsonString: String = ""
                while (resultSet.next()) {
                      println("mem_ID: ${resultSet.getInt("mem_id")}")
                    // 샘플 객체
                    val user = Person1(resultSet.getString("mem_id"), resultSet.getString("mem_name"))

// 객체를 JSON 문자열로 변환
                    jsonString = objectMapper.writeValueAsString(user)
                    println("=============>${jsonString}")
                    //call.respond(resultSet.getString("mem_id"))
                    call.respondText(jsonString)

// 출력


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







