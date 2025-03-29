package com.pjt.brandpricing.adapter.controller.data.brand

import com.pjt.brandpricing.application.domain.data.out.UpdateBrandResult

data class UpdateBrandResponse(
    val brandId: String,
    val brandName: String,
    val brandStatus: String,
) {
    companion object {
        fun of(result: UpdateBrandResult): UpdateBrandResponse {
            return UpdateBrandResponse(
                brandId = result.brandId.value,
                brandName = result.brandName,
                brandStatus = result.brandStatus.value
            )
        }
    }
}

