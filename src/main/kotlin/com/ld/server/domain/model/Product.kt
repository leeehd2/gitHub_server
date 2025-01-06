package com.ld.server.domain.model

import com.ld.server.implementation.BaseEntity
import com.ld.server.implementation.BaseEntityClass
import com.ld.server.implementation.BaseLongIdTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Table.Dual.enumerationByName
import org.jetbrains.exposed.sql.Table.Dual.integer
import org.jetbrains.exposed.sql.Table.Dual.varchar

enum class ProductCategory {
    FOODS, BOOKS, ELECTRONICS
}

object Products : BaseLongIdTable("product", "product_id") {
    val name = varchar("product_name", 255)
    val category = enumerationByName<ProductCategory>("category", 255)
    val description = varchar("description", 255)
    val price = integer("price")
}

class Product(id: EntityID<Long>) : BaseEntity(id, Products) {
    companion object : BaseEntityClass<Product>(Products)
    var name by Products.name
    var category by Products.category
    var description by Products.description
    var price by Products.price
}
