package com.pjt.brandpricing.adapter.controller

class UrlConstants {
    companion object {
        /** 브랜드 */
        const val 브랜드_생성 = "/api/bp/v1/brand";
        const val 브랜드_업데이트 = "/api/bp/v1/brand";

        /** 상품 */
        const val 상품_생성 = "/api/bp/v1/product";
        const val 상품_업데이트 = "/api/bp/v1/product";

        /** 조회 */
        const val 최저가격_카테고리_조회 = "/api/bp/v1/summary/min/category";
        const val 최저가격_브랜드_조회 = "/api/bp/v1/summary/min/brand";
        const val 최고최저_카테고리_조회 = "/api/bp/v1/summary/max-min/category/{categoryName}";
    }
}
