package com.pjt.brandpricing.application.domain

import com.pjt.brandpricing.application.domain.enums.ProductStatus
import com.pjt.brandpricing.support.EntityId

class BpProduct(
    val seq: Long? = null,
    val productId: EntityId,
    var productStatus: ProductStatus,
    var productName: String,
    var price: BpPrice,
) {

    companion object {
        fun create(
            productName: String,
            price: BpPrice,
        ): BpProduct {
            return BpProduct(
                productId = EntityId(),
                productName = productName,
                price = price,
                productStatus = ProductStatus.DISPLAY
            )
        }
    }

    fun update(
        productName: String?,
        price: BpPrice?,
        productStatus: ProductStatus?,
    ) {
        productName?.let { this.productName = it }
        price?.let { this.price = it }
        productStatus?.let { this.productStatus = it }
    }
}
