package com.pjt.brandpricing.application.domain

import com.pjt.brandpricing.application.domain.enums.BrandStatus
import com.pjt.brandpricing.support.EntityId

class BpBrand(
    val seq: Long? = null,
    val brandId: EntityId,
    var brandStatus: BrandStatus,
    var brandName: String,
) {
    companion object {
        fun create(brandName: String): BpBrand {
            return BpBrand(
                brandId = EntityId(),
                brandStatus = BrandStatus.ACTIVE,
                brandName = brandName
            )
        }
    }

    fun update(
        brandStatus: BrandStatus?,
        brandName: String?,
    ) {
        brandStatus?.let { this.brandStatus = it }
        brandName?.let { this.brandName = it }
    }
}
