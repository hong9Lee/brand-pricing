package com.pjt.brandpricing.fake

import com.pjt.brandpricing.application.domain.data.port.command.BpCategoryCommandPort
import com.pjt.brandpricing.infra.persistence.entity.BpCategoryEntity
import com.pjt.brandpricing.support.EntityId

class FakeBpCategoryCommandPort : BpCategoryCommandPort {
    private val categories = mutableListOf<BpCategoryEntity>()

    override fun existsByCategoryId(categoryId: EntityId): Boolean {
        return categories.any { it.categoryId == categoryId }
    }
}
