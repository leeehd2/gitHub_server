package com.ld.server.api.router

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.ld.server.work.DatabaseCon
import io.github.smiley4.ktorswaggerui.dsl.routing.post
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import java.sql.Connection
import java.sql.ResultSet

fun Route.userSearch() {
        get("/users11") {
            println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@22")
            val userid = call.parameters["userid"]
            val password = call.parameters["password"]
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
                var resultSet: ResultSet =
                    statement.executeQuery("SELECT mem_id , mem_name FROM dongdb.member_tb where mem_id = '${userid}' and password = '${password}' ")

                var jsonString: String = ""
            try {
                println("resultSet.isLast: ${resultSet.isBeforeFirst}")

                while (resultSet.isBeforeFirst) {
                    println("mem_ID: ${resultSet.getInt("mem_id")}")
                    // 샘플 객체
                    val user1 = user(resultSet.getInt("mem_id"), resultSet.getString("mem_name"))

// 객체를 JSON 문자열로 변환
                    jsonString = objectMapper.writeValueAsString(user1)
                    println("=============>${jsonString}")
                    //call.respond(resultSet.getString("mem_id"))
                    // 리소스 정리

                }
            } catch (e: Exception) {
                println("try_catch=================${e.printStackTrace()}")
            }




                call.respondText(jsonString)


                resultSet.close()
                statement.close()
                conn.close()



        }



        post("/usersSignUp") {
            println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@22")
            val memName = call.parameters["memName"]
            val password = call.parameters["password"]
            val email = call.parameters["email"]
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

            println("${memName}=========================${password}")
            // 데이터 조회
            // 데이터 조회
            var cnt : Int? = 0
            var statement = conn.createStatement()
           // var resultSet: ResultSet =
              cnt =  statement.executeUpdate("insert into dongdb.member_tb(mem_name,password,email) values('${memName}','${password}','${email}')")

            println("${cnt}=========================")
/*
            var jsonString: String = ""
            while (resultSet.next()) {
                println("mem_ID: ${resultSet.getInt("mem_id")}")
                // 샘플 객체
                val user1 = user(resultSet.getInt("mem_id"), resultSet.getString("mem_name"))
*/
// 객체를 JSON 문자열로 변환

            statement.close()
            conn.close()
            call.respondText("성공")




          //}

            // 리소스 정리



        }

    }




internal data class user // 생성자, getter, setter
    (var memId: Int, var memName: String)







