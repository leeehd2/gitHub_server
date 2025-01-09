package com.ld.server.core.configuration

import com.ld.server.core.configuration.DatabaseUtils.connectDatabase
import com.ld.server.core.util.ApplicationConfigUtils.getDataSource
import com.ld.server.core.util.ApplicationConfigUtils.isDevEnv
import com.ld.server.domain.model.Orders
import com.ld.server.domain.model.Products
import com.ld.server.implementation.Users
import io.ktor.server.application.Application
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabase() {

    println("configureDatabase=========================")
    connectDatabase()
   // if (isResetTables()) dropAndCreateTables()

   // if (isSetDummyData()) setDummyData()

  //  if (isResetTables()) dropAndCreateTables1()
}

object DatabaseUtils {
    private val tables = arrayOf(Users, Orders, Products)
    private val log = logger()
    fun connectDatabase() {
      val  jdbcUrl1 = getDataSource("url")
      val  username1 = getDataSource("username")
      val  password1 = getDataSource("password")
      val  driverClassName1 = getDataSource("driver-class-name")
      val  maximumPoolSize1 = getDataSource("max-pool-size").toInt()
      val  isAutoCommit1 = false
      val  transactionIsolation1 = "TRANSACTION_REPEATABLE_READ"

        /*
        val hikari = HikariDataSource(
            HikariConfig().apply {
                jdbcUrl= getDataSource("url")
                username = getDataSource("username")
                password = getDataSource("password")
                driverClassName = getDataSource("driver-class-name")
                maximumPoolSize = getDataSource("max-pool-size").toInt()
                isAutoCommit = false
                transactionIsolation = "TRANSACTION_REPEATABLE_READ"
                validate()
            }
        )
        Database.connect(hikari)
        log.info("Database is successfully connected.")
*/
/*
        jdbcUrl = getDataSource("url")
        username = getDataSource("username")
        password = getDataSource("password")
        driverClassName = getDataSource("driver-class-name")
        maximumPoolSize = getDataSource("max-pool-size").toInt()
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
*/
      //  val jdbcUrl = "jdbc:mysql://localhost:3306/testdb"
      //  val username = "root"
      //  val password = "password"

        // 데이터베이스 연결
       // println("데이터베이스 연결 시도====")
       // val connection: Connection = DriverManager.getConnection(jdbcUrl1, username1, password1)
       // println("jdbcUrl1====$jdbcUrl1")
       // println("username1====$username1")
      //  println("password1====$password1")
/*
        // 데이터 조회
        var statement = connection.createStatement()
        var resultSet: ResultSet = statement.executeQuery("SELECT * FROM user")

        // 결과 출력
        println("00000000=================")
        while (resultSet.next()) {
            println("ID: ${resultSet.getInt("user_id")}, Name: ${resultSet.getString("nickname")}")
        }

        resultSet = statement.executeQuery("SELECT * FROM product  limit 100")

        // 결과 출력
        println("1111111111=================")
        while (resultSet.next()) {
            println("product_ID: ${resultSet.getInt("product_id")}, product_Name: ${resultSet.getString("product_name")}")
        }

        // 리소스 정리
        resultSet.close()
        statement.close()
        connection.close()
*/
    }

    fun dropAndCreateTables() = transaction {
        //SchemaUtils.drop(*tables)
        //SchemaUtils.create(*tables)
        log.info("${tables.size} tables are successfully dropped and created.")

       Users.insert {
            it[nickname] = "dong1"
            it[email] = "dong2"
            it[encPassword] = "dong3"
            it[nickname] = "dong4"
        }

    }


/*
    fun dropAndCreateTables1() = transaction {
        println("00000000=================")
        Products.selectAll().forEach {
            println("${it[Products.description]}")
        }

    }
*/
    fun <T> dbQuery(
        block: () -> T
    ): T = transaction {
        if (isDevEnv()) addLogger(StdOutSqlLogger)
        block()
    }
}
