package com.pjt.brandpricing.application.domain.event

import com.pjt.brandpricing.support.EntityId

data class BpProductSummaryEvent(
    val productId: EntityId
)

