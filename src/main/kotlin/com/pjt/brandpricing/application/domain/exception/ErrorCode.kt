package com.pjt.brandpricing.application.domain.exception

enum class ErrorCode(
    val code: String,
    val message: String,
) {

    /** BRAND */
    NOT_FOUND_BRAND("NOT_FOUND_BRAND", "존재하지 않는 브랜드 입니다."),

    /** PRODUCT */
    NOT_FOUND_PRODUCT("NOT_FOUND_PRODUCT", "존재하지 않는 상품 입니다."),

    /** CATEGORY */
    NOT_FOUND_CATEGORY_PRODUCT("NOT_FOUND_CATEGORY_PRODUCT", "카테고리에 속한 상품이 없습니다."),

    /** PRODUCT_MAPPING */
    NOT_FOUND_PRODUCT_MAPPING("NOT_FOUND_PRODUCT_MAPPING", "상품 매핑 정보가 존재하지 않습니다."),

    /** SUMMARY */
    NOT_FOUND_SUMMARY("NOT_FOUND_SUMMARY", "상품 가격 통계 데이터가 존재하지 않습니다.");
}
