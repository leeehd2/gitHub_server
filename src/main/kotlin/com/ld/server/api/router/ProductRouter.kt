package com.ld.server.api.router

import com.ld.server.api.dto.GetProductRequest
import com.ld.server.api.dto.GetProductResponse
import com.ld.server.api.util.PaginationUtils
import com.ld.server.api.util.RequestUtils.getQueryParams
import com.ld.server.api.util.SwaggerUtils.internalServerError
import com.ld.server.domain.service.ProductService
import io.github.smiley4.ktorswaggerui.dsl.routing.get
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject

val productTags = listOf("Product")

fun Route.productRouter() {
    val productService: ProductService by inject()

    get("/products", {
        tags = productTags
        description = "get all products"
        response {
            HttpStatusCode.OK to {
                body<PaginationUtils.PageResponse<GetProductResponse>>()
            }
            HttpStatusCode.InternalServerError to internalServerError()
        }
    }) {
        val params = call.getQueryParams<GetProductRequest>()
        call.respond(productService.getAllProducts(params))
    }
}
