package com.pjt.brandpricing.infra.persistence.adapter.command

import com.pjt.brandpricing.application.domain.data.port.command.BpCategoryCommandPort
import com.pjt.brandpricing.infra.persistence.repository.command.BpCategoryRepository
import com.pjt.brandpricing.support.EntityId
import org.springframework.stereotype.Component

@Component
class BpCategoryCommandAdapter(
    private val bpCategoryRepository: BpCategoryRepository,
): BpCategoryCommandPort {
    override fun existsByCategoryId(categoryId: EntityId): Boolean {
        return bpCategoryRepository.existsByCategoryId(categoryId)
    }

}
