package com.pjt.brandpricing.application.domain.data.`in`

import com.pjt.brandpricing.adapter.controller.data.brand.UpdateBrandRequest
import com.pjt.brandpricing.application.domain.enums.BrandStatus
import com.pjt.brandpricing.support.EntityId

data class UpdateBrandCommand(
    val brandId: EntityId,
    val brandName: String?,
    val brandStatus: BrandStatus?
) {
    companion object {
        fun of(request: UpdateBrandRequest): UpdateBrandCommand {
            return UpdateBrandCommand(
                brandId = EntityId(request.brandId),
                brandName = request.brandName,
                brandStatus = request.brandStatus?.let { BrandStatus.valueOf(it) }
            )
        }
    }
}
