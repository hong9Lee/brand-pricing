package com.pjt.brandpricing.adapter.controller.data.brand

import com.pjt.brandpricing.application.domain.data.out.CreateBrandResult

class CreateBrandResponse(
    val brandId: String,
    val brandName: String,
    val brandStatus: String
) {
    companion object {
        fun of(result: CreateBrandResult): CreateBrandResponse {
            return CreateBrandResponse(
                brandId = result.brandId.value,
                brandName = result.brandName,
                brandStatus = result.brandStatus.value
            )
        }
    }
}
