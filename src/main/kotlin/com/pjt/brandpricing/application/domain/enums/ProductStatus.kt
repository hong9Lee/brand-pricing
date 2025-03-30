package com.pjt.brandpricing.application.domain.enums

enum class ProductStatus(
    private val value: String
) {
    DISPLAY("판매 중"),
    SOLD_OUT("품절"),
    HIDDEN("비노출"),
    DELETED("삭제");
}
