package com.pjt.brandpricing.fake

import com.pjt.brandpricing.application.domain.BpCategory
import com.pjt.brandpricing.application.domain.data.port.command.BpCategoryCommandPort
import com.pjt.brandpricing.support.EntityId

class FakeBpCategoryCommandPort : BpCategoryCommandPort {
    private val categories = mutableListOf<BpCategory>()

    override fun existsByCategoryId(categoryId: EntityId): Boolean {
        return categories.any { it.categoryId == categoryId }
    }

    fun save(category: BpCategory) {
        categories.add(category)
    }

    fun save(categoryId: EntityId) {
        categories.add(
            BpCategory(
                categoryId = categoryId,
                categoryName = "testCategory"
            )
        )
    }
}
