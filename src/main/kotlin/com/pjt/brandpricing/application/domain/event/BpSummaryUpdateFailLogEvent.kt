package com.pjt.brandpricing.application.domain.event

import com.pjt.brandpricing.support.EntityId

data class BpSummaryUpdateFailLogEvent(
    val brandId: EntityId,
    val categoryId: EntityId,
    val productId: EntityId,
    val errorMessage: String?
)
