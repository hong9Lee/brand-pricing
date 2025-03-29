package com.pjt.brandpricing.application.domain.data.out

import com.pjt.brandpricing.application.domain.BpBrand
import com.pjt.brandpricing.application.domain.enums.BrandStatus
import com.pjt.brandpricing.support.EntityId

data class CreateBrandResult(
    val brandId: EntityId,
    val brandName: String,
    val brandStatus: BrandStatus,
) {
    companion object {
        fun of(bpBrand: BpBrand): CreateBrandResult {
            return CreateBrandResult(
                brandId = bpBrand.brandId,
                brandName = bpBrand.brandName,
                brandStatus = bpBrand.brandStatus,
            )
        }
    }
}
