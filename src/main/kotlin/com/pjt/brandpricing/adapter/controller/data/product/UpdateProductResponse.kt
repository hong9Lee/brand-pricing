package com.pjt.brandpricing.adapter.controller.data.product

import com.pjt.brandpricing.application.domain.data.out.UpdateProductResult
import java.math.BigDecimal

data class UpdateProductResponse(
    val productId: String,
    val productName: String,
    val price: BigDecimal,
    val productStatus: String
) {
    companion object {
        fun of(result: UpdateProductResult): UpdateProductResponse = UpdateProductResponse(
            productId = result.productId.value,
            productName = result.productName,
            price = result.price.value,
            productStatus = result.productStatus.name
        )
    }
}

