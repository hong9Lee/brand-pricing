package com.pjt.brandpricing.infra.persistence.entity

import com.pjt.brandpricing.application.domain.BpSummaryUpdateFailLog
import com.pjt.brandpricing.infra.persistence.entity.base.BaseTimeEntity
import com.pjt.brandpricing.support.EntityId
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "bp_summary_update_fail_log")
class BpSummaryUpdateFailLogEntity(

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "brand_id"))
    val brandId: EntityId,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "category_id"))
    val categoryId: EntityId,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "product_id"))
    val productId: EntityId,

    @Column(name = "error_message")
    val errorMessage: String?,

    @Column(name = "is_active", nullable = false)
    var isActive: Boolean = true,
) : BaseTimeEntity() {

    fun toDomain(): BpSummaryUpdateFailLog {
        return BpSummaryUpdateFailLog(
            seq = seq,
            brandId = brandId,
            categoryId = categoryId,
            productId = productId,
            errorMessage = errorMessage,
            isActive = isActive,
        )
    }

    companion object {
        fun of(bpSummaryUpdateFailLog: BpSummaryUpdateFailLog): BpSummaryUpdateFailLogEntity {
            return BpSummaryUpdateFailLogEntity(
                brandId = bpSummaryUpdateFailLog.brandId,
                categoryId = bpSummaryUpdateFailLog.categoryId,
                productId = bpSummaryUpdateFailLog.productId,
                errorMessage = bpSummaryUpdateFailLog.errorMessage,
                isActive = bpSummaryUpdateFailLog.isActive ?: true
            ).apply {
                seq = bpSummaryUpdateFailLog.seq
            }
        }
    }
}
