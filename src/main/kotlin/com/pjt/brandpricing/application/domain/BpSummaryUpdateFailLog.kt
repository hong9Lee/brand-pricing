package com.pjt.brandpricing.application.domain

import com.pjt.brandpricing.support.EntityId

class BpSummaryUpdateFailLog(
    val seq: Long? = null,
    val brandId: EntityId,
    val categoryId: EntityId,
    val productId: EntityId,
    val errorMessage: String?,
    val isActive: Boolean? = true,
) {
    companion object {
        fun of(
            brandId: EntityId,
            categoryId: EntityId,
            productId: EntityId,
            errorMessage: String?,
        ): BpSummaryUpdateFailLog {
            return BpSummaryUpdateFailLog(
                brandId = brandId,
                categoryId = categoryId,
                productId = productId,
                errorMessage = errorMessage
            )
        }
    }
}
