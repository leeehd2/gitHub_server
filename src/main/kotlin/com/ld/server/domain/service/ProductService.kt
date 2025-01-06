package com.ld.server.domain.service

import com.ld.server.api.dto.CreateProductRequest
import com.ld.server.api.dto.GetProductRequest
import com.ld.server.api.dto.GetProductResponse
import com.ld.server.api.dto.IdResponse
import com.ld.server.api.util.PaginationUtils.PageResponse
import com.ld.server.core.util.toResponse
import com.ld.server.domain.model.ProductRepository

class ProductService(
    private val productRepository: ProductRepository
) {
    suspend fun createProduct(request: CreateProductRequest): IdResponse {
        val product = productRepository.save(request)
        return product.id.value.toResponse()
    }
    suspend fun getAllProducts(request: GetProductRequest): PageResponse<GetProductResponse> {
        return productRepository.findAllByCondition(request)
    }
}
