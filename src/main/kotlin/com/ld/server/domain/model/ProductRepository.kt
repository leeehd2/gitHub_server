package com.ld.server.domain.model

import com.ld.server.api.dto.CreateProductRequest
import com.ld.server.api.dto.GetProductRequest
import com.ld.server.api.dto.GetProductResponse
import com.ld.server.api.dto.ProductPriceCondition
import com.ld.server.api.util.PaginationUtils
import com.ld.server.api.util.PaginationUtils.toPageResponse
import com.ld.server.core.configuration.DatabaseUtils.dbQuery
import com.ld.server.domain.model.Product
import com.ld.server.domain.model.Products
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.selectAll

interface ProductRepository {
    suspend fun save(request: CreateProductRequest): Product
    suspend fun findById(id: Long): Product
    suspend fun findAllByCondition(request: GetProductRequest): PaginationUtils.PageResponse<GetProductResponse>
}

class ProductRepositoryImpl : ProductRepository {
    override suspend fun save(request: CreateProductRequest) = dbQuery {
        Product.new {
            this.name = request.name
            this.description = request.description
            this.price = request.price
        }
    }

    override suspend fun findById(id: Long) = dbQuery {
        Product.findById(id) ?: throw NoSuchElementException()
    }

    override suspend fun findAllByCondition(request: GetProductRequest): PaginationUtils.PageResponse<GetProductResponse> = dbQuery {
        val query = Products.selectAll()
        request.name?.let { query.andWhere { Products.name like it } }
        request.price?.let {
            when (request.priceCondition) {
                ProductPriceCondition.LESS_THAN -> query.andWhere { Products.price less it }
                ProductPriceCondition.GREATER_THAN -> query.andWhere { Products.price greater it }
                else -> query.andWhere { Products.price eq it }
            }
        }
        request.category?.let { query.andWhere { Products.category eq it } }
        val totalCount = query.count()
        query.limit(request.limit, request.offset)
        val list = query.map { Product.wrapRow(it) }.map { GetProductResponse(it) }
        return@dbQuery list.toPageResponse(request.offset, request.limit, totalCount)
    }
}
