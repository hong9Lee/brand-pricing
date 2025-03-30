package com.pjt.brandpricing.application.domain

import com.pjt.brandpricing.support.EntityId

class BpProductMapping(
    val seq: Long? = null,
    val brandId: EntityId,
    val categoryId: EntityId,
    val productId: EntityId,
    var isActive: Boolean = true,
) {
    companion object {
        fun create(
            brandId: EntityId,
            categoryId: EntityId,
            productId: EntityId
        ): BpProductMapping {
            return BpProductMapping(
                brandId = brandId,
                categoryId = categoryId,
                productId = productId,
                isActive = true
            )
        }
    }
}
