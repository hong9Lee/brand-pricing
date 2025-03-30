package com.pjt.brandpricing.application.domain.data.`in`

import com.pjt.brandpricing.adapter.controller.data.product.CreateProductRequest
import com.pjt.brandpricing.application.domain.BpPrice
import com.pjt.brandpricing.support.EntityId

data class CreateProductCommand(
    val productName: String,
    val price: BpPrice,
    val brandId: EntityId,
    val categoryId: EntityId,
) {
    companion object {
        fun of(req: CreateProductRequest): CreateProductCommand = CreateProductCommand(
            productName = req.productName,
            price = BpPrice(req.price),
            brandId = EntityId(req.brandId),
            categoryId = EntityId(req.categoryId)
        )
    }
}
