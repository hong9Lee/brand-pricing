package com.pjt.brandpricing.adapter.controller.data.product

import com.pjt.brandpricing.application.domain.data.out.CreateProductResult
import java.math.BigDecimal

data class CreateProductResponse(
    val productId: String,
    val productName: String,
    val price: BigDecimal,
    val productStatus: String,
) {
    companion object {
        fun of(result: CreateProductResult): CreateProductResponse = CreateProductResponse(
            productId = result.productId.value,
            productName = result.productName,
            price = result.price.value,
            productStatus = result.productStatus.name
        )
    }
}
