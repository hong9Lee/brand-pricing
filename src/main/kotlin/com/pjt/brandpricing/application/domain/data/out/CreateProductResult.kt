package com.pjt.brandpricing.application.domain.data.out

import com.pjt.brandpricing.application.domain.BpPrice
import com.pjt.brandpricing.application.domain.BpProduct
import com.pjt.brandpricing.application.domain.enums.ProductStatus
import com.pjt.brandpricing.support.EntityId

data class CreateProductResult(
    val productId: EntityId,
    val productName: String,
    val price: BpPrice,
    val productStatus: ProductStatus
) {
    companion object {
        fun of(product: BpProduct): CreateProductResult = CreateProductResult(
            productId = product.productId,
            productName = product.productName,
            price = product.price,
            productStatus = product.productStatus
        )
    }
}

