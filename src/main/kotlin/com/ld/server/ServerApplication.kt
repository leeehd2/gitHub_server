package com.ld.server



//import io.ktor.server.plugins.contentnegotiation.ContentNegotiation

//import androidx.lifecycle.lifecycleScope
import com.example.api.configuration.configureRouters
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.ld.server.api.configuration.configureAuthentication
import com.ld.server.api.configuration.configureExceptionHandling
//import com.ld.server.api.configuration.configureRouters
import com.ld.server.api.configuration.configureSwagger
import com.ld.server.api.router.Person
import com.ld.server.core.configuration.configureDependencyInjection
import com.ld.server.core.configuration.configureLogging
import com.ld.server.core.configuration.configureSerialization
import com.ld.server.work.DatabaseCon
import com.ld.server.work.DatabaseCon.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.coroutines.*
import java.sql.Connection
import java.sql.ResultSet


class Application

fun main() {
	embeddedServer(Netty, port = 8080, module = Application::module)
		.start(wait = true)

	///runBlocking {
	//	sendPostRequest()
	//}



}

/****
 * test
 */


	fun Application.module() {
		println("start")
		val DbCon = DatabaseCon()
		DbCon.conn()
		println("DbCon.conn=3===$DbCon.conn")
		DbCon.conn?.let { test(conn = it) }
	  	//configureDatabase()
		println("configureDatabase")
		//dataSearch()
		//DbCon.conn?.let { dataSearch(conn = it)}

	    configureLogging()
		println("configureLogging")
	   configureExceptionHandling()
		println("configureExceptionHandling")

	   configureSerialization()
	   println("configureSerialization")
	   configureDependencyInjection()
		println("configureDependencyInjection")
	   configureRouters()
	   configureSwagger()
	   configureAuthentication()

		val objectMapper = ObjectMapper()
		// SerializationFeature.INDENT_OUTPUT 활성화
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT)
		// 샘플 객체
		val person = Person("홍길동", 30)
		// 객체를 JSON 문자열로 변환
		val jsonString = objectMapper.writeValueAsString(person)
		// 출력
		println("fffffff=000============>"+jsonString)



	/*
		val client = HttpClient(CIO)
		lifecycleScope.launch {
			val client = HttpClient(CIO) {
				install(ContentNegotiation) {
					json(Json { prettyPrint = true })
				}
			}
		}  */
			// 텍스트 데이터 전송
			////val responseText = client.post("http://localhost:8080/submit") {
			//	setBody("This is a test")
			//}
			//println("Response: ${responseText.bodyAsText()}")
/*
			// JSON 데이터 전송
			val user = User(name = "Alice", age = 25)
			val responseJson: HttpResponse = client.post("http://localhost:8080/json") {
				contentType(ContentType.Application.Json)
				setBody(user)
			}
			println("Response: ${responseJson.bodyAsText()}")

			client.close()


		}  */


	}


internal class Person // 생성자, getter, setter
	(var name: String, var age: Int)


fun test(conn : Connection ){
	println("test=================")
	// 데이터 조회
	// 데이터 조회
	var statement = conn.createStatement()
	var resultSet: ResultSet = statement.executeQuery("SELECT * FROM product  limit 100")

	// 결과 출력
	println("product=================")
	while (resultSet.next()) {
		println("product_ID: ${resultSet.getInt("product_id")}, product_Name: ${resultSet.getString("product_name")}")
	}

	// 리소스 정리
	resultSet.close()
	statement.close()
	//conn.close()
}


