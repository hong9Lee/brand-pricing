package com.pjt.brandpricing.application.domain

import com.pjt.brandpricing.support.EntityId

class BpCategory(
    val seq: Long? = null,
    val categoryId: EntityId,
    var categoryName: String,
) {
}
