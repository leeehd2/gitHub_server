package com.ld.server.api.router

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.ld.server.work.DatabaseCon
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import java.sql.Connection
import java.sql.ResultSet

fun Route.dataSearch(){
    val objectMapper = ObjectMapper()
    val Dataconn: DatabaseCon = DatabaseCon()
    Dataconn.conn()
    val conn: Connection = Dataconn.conn!!

    /**
     * git test 2025.01.06
     *
     *
     */




// SerializationFeature.INDENT_OUTPUT 활성화
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT)

    val personList = mutableListOf<Person1>()

        println("test=================")
        // 데이터 조회
        // 데이터 조회
        var statement = conn.createStatement()
        var resultSet: ResultSet = statement.executeQuery("SELECT * FROM product  limit 10")

        // 결과 출력
        println("product=================")
       var jsonString: String = ""
        while (resultSet.next()) {
          //  println("product_ID: ${resultSet.getInt("product_id")}, product_Name: ${resultSet.getString("product_name")}")
            // 샘플 객체
            val person1 = Person1(resultSet.getInt("product_id").toString(), resultSet.getString("product_name"))
            personList.add(person1)
// 객체를 JSON 문자열로 변환
             jsonString = objectMapper.writeValueAsString(personList)



// 출력
           // println("=============>${jsonString}")

        }


    //println("선임아.")
    //println("다이어트 ")
    //println("열심히 해라")
    var jang: String = "선임아 "
        jang += "다이어트 "
        jang += "열심히 해라"

    get("/leedong"){
        call.respondText(jsonString)
    }

    get("/jang"){
        call.respondText(jang)
    }

        // 리소스 정리
        resultSet.close()
        statement.close()
        conn.close()
/*
    routing {

        // 기본 라우트
        get("/") {
            // sendPostRequest()
            call.respondText("Welcome to the home page!")
        }
    }
    }

*/


}


internal class Person1 // 생성자, getter, setter
    (var name: String, var age: String)







