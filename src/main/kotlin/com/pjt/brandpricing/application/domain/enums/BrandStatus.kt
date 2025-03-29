package com.pjt.brandpricing.application.domain.enums

enum class BrandStatus(
    val value: String
) {
    ACTIVE("입점"),
    INACTIVE("비입점"),
    DELETED("삭제");
}
