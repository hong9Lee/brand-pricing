package com.pjt.brandpricing.infra.persistence.entity

import com.pjt.brandpricing.application.domain.BpCategory
import com.pjt.brandpricing.infra.persistence.entity.base.BaseTimeEntity
import com.pjt.brandpricing.support.EntityId
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "bp_category")
class BpCategoryEntity(

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "category_id"))
    val categoryId: EntityId,

    @Column(name = "category_name", nullable = false)
    var categoryName: String,

    ) : BaseTimeEntity() {

    fun toDomain(): BpCategory = BpCategory(
        seq = seq,
        categoryId = categoryId,
        categoryName = categoryName
    )

    companion object {
        fun of(bpCategory: BpCategory): BpCategoryEntity = BpCategoryEntity(
            categoryId = bpCategory.categoryId,
            categoryName = bpCategory.categoryName
        ).apply { this.seq = bpCategory.seq }
    }
}
