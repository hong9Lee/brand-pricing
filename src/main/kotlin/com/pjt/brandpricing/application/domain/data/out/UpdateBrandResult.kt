package com.pjt.brandpricing.application.domain.data.out

import com.pjt.brandpricing.application.domain.BpBrand
import com.pjt.brandpricing.application.domain.enums.BrandStatus
import com.pjt.brandpricing.support.EntityId

data class UpdateBrandResult(
    val brandId: EntityId,
    val brandName: String,
    val brandStatus: BrandStatus,
) {
    companion object {
        fun of(bpBrand: BpBrand): UpdateBrandResult {
            return UpdateBrandResult(
                brandId = bpBrand.brandId,
                brandName = bpBrand.brandName,
                brandStatus = bpBrand.brandStatus,
            )
        }
    }
}
