package com.pjt.brandpricing.application.domain.data.`in`

import com.pjt.brandpricing.adapter.controller.data.product.UpdateProductRequest
import com.pjt.brandpricing.application.domain.BpPrice
import com.pjt.brandpricing.application.domain.enums.ProductStatus
import com.pjt.brandpricing.support.EntityId

data class UpdateProductCommand(
    val productId: EntityId,
    val productName: String?,
    val price: BpPrice?,
    val productStatus: ProductStatus?,
) {
    companion object {
        fun of(req: UpdateProductRequest): UpdateProductCommand = UpdateProductCommand(
            productId = EntityId(req.productId),
            productName = req.productName,
            price = req.price?.let { BpPrice(it) },
            productStatus = req.productStatus?.let { ProductStatus.valueOf(it) }
        )
    }
}
