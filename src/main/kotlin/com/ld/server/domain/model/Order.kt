package com.ld.server.domain.model

import com.ld.server.domain.model.Product
import com.ld.server.domain.model.Products
import com.ld.server.implementation.BaseEntity
import com.ld.server.implementation.BaseEntityClass
import com.ld.server.implementation.BaseLongIdTable
import com.ld.server.implementation.UserExposedEntity
import com.ld.server.implementation.Users
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Table.Dual.enumerationByName
import org.jetbrains.exposed.sql.Table.Dual.reference

enum class OrderStatus(val description: String) {
    PENDING("주문진행중"),
    PAYMENT_COMPLETED("결제완료"),
    PAYMENT_FAILED("결제실패"),
    COMPLETED("주문완료"),
    DELIVERING("배송중"),
    DELIVERED("배송완료"),
    CANCELED("취소됨"),
    REFUNDING("환불중"),
    REFUNDED("환불됨")
}

object Orders : BaseLongIdTable("order", "order_id") {
    val status = enumerationByName<OrderStatus>("status", 255)
    val product = reference("product_id", Products)
    val user = reference("user_id", Users)
}

class Order(id: EntityID<Long>) : BaseEntity(id, Orders) {
    companion object : BaseEntityClass<Order>(Orders)
    var status by Orders.status
    var product by Product referencedOn Orders.product
    var userExposedEntity by UserExposedEntity referencedOn Orders.user
}
