package com.pjt.brandpricing.application.domain.data.port.command

import com.pjt.brandpricing.support.EntityId

interface BpCategoryCommandPort {
    fun existsByCategoryId(categoryId: EntityId): Boolean
}
