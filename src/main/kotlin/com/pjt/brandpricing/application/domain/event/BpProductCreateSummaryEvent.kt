package com.pjt.brandpricing.application.domain.event

import com.pjt.brandpricing.support.EntityId

data class BpProductCreateSummaryEvent(
    val productId: EntityId
)

