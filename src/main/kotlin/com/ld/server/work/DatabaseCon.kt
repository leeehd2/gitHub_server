package com.ld.server.work

import com.ld.server.core.util.ApplicationConfigUtils.getDataSource
import java.sql.Connection
import java.sql.DriverManager


open class DatabaseCon() {
    var conn: Connection? = null
    val jdbcUrl1: String = getDataSource("url")
    val username1: String = getDataSource("username")
    val password1: String = getDataSource("password")
    val driverClassName1: String = getDataSource("driver-class-name")
    val maximumPoolSize1: String = getDataSource("max-pool-size").toInt().toString()
    val isAutoCommit1: String = false.toString()
    val transactionIsolation1: String = "TRANSACTION_REPEATABLE_READ"

    fun conn() {
        // 데이터베이스 연결
        println("데이터베이스 연결 시도====")
        println("jdbcUrl1==00==$jdbcUrl1")
        println("username1=2===$username1")
        println("password1=3===$password1")
        conn = DriverManager.getConnection(jdbcUrl1, username1, password1)
        println("jdbcUrl1==0==$jdbcUrl1")
        println("username1=2===$username1")
        println("password1=3===$password1")
    }
}