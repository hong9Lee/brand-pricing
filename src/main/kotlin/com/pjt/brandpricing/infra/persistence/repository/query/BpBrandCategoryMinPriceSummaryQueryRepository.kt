package com.pjt.brandpricing.infra.persistence.repository.query

import com.pjt.brandpricing.application.domain.enums.PriceType
import com.pjt.brandpricing.application.domain.exception.ApplicationException
import com.pjt.brandpricing.application.domain.exception.ErrorCode
import com.pjt.brandpricing.infra.persistence.entity.QBpBrandCategoryPriceSummaryEntity.bpBrandCategoryPriceSummaryEntity
import com.pjt.brandpricing.infra.persistence.entity.QBpBrandEntity.bpBrandEntity
import com.pjt.brandpricing.infra.persistence.entity.QBpCategoryEntity.bpCategoryEntity
import com.pjt.brandpricing.infra.persistence.model.BpBrandCategoryMinMaxPriceSummaryResult
import com.pjt.brandpricing.infra.persistence.model.BpBrandCategoryMinPriceSummaryResult
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component

@Component
class BpBrandCategoryMinPriceSummaryQueryRepository(
    private val jpaQueryFactory: JPAQueryFactory,
) {

    fun findAllBrandCategoryPriceSummaries(): List<BpBrandCategoryMinPriceSummaryResult> {
        return jpaQueryFactory
            .select(
                Projections.constructor(
                    BpBrandCategoryMinPriceSummaryResult::class.java,
                    bpCategoryEntity.categoryName,
                    bpBrandEntity.brandName,
                    bpBrandCategoryPriceSummaryEntity.minPrice
                )
            )
            .from(bpBrandCategoryPriceSummaryEntity)
            .join(bpCategoryEntity).on(bpBrandCategoryPriceSummaryEntity.categoryId.eq(bpCategoryEntity.categoryId))
            .join(bpBrandEntity).on(bpBrandCategoryPriceSummaryEntity.brandId.eq(bpBrandEntity.brandId))
            .orderBy(
                bpBrandEntity.seq.desc(),
                bpCategoryEntity.seq.asc()
            )
            .fetch()
    }

    fun findMinPriceByCategoryName(categoryName: String): List<BpBrandCategoryMinMaxPriceSummaryResult> {
        return findExtremePriceByCategoryName(categoryName, PriceType.MIN)
    }

    fun findMaxPriceByCategoryName(categoryName: String): List<BpBrandCategoryMinMaxPriceSummaryResult> {
        return findExtremePriceByCategoryName(categoryName, PriceType.MAX)
    }

    private fun findExtremePriceByCategoryName(
        categoryName: String,
        priceType: PriceType,
    ): List<BpBrandCategoryMinMaxPriceSummaryResult> {

        val priceExpression = when (priceType) {
            PriceType.MIN -> bpBrandCategoryPriceSummaryEntity.minPrice.value
            PriceType.MAX -> bpBrandCategoryPriceSummaryEntity.maxPrice.value
        }

        val extremePriceExpression = when (priceType) {
            PriceType.MIN -> priceExpression.min()
            PriceType.MAX -> priceExpression.max()
        }

        val extremePrice = jpaQueryFactory
            .select(extremePriceExpression)
            .from(bpBrandCategoryPriceSummaryEntity)
            .join(bpCategoryEntity).on(bpBrandCategoryPriceSummaryEntity.categoryId.eq(bpCategoryEntity.categoryId))
            .where(bpCategoryEntity.categoryName.eq(categoryName))
            .fetchOne()

        if (extremePrice == null) {
            throw ApplicationException.ofNotFound(ErrorCode.NOT_FOUND_CATEGORY)
        }

        val selectedPrice = when (priceType) {
            PriceType.MIN -> bpBrandCategoryPriceSummaryEntity.minPrice
            PriceType.MAX -> bpBrandCategoryPriceSummaryEntity.maxPrice
        }

        return jpaQueryFactory
            .select(
                Projections.constructor(
                    BpBrandCategoryMinMaxPriceSummaryResult::class.java,
                    bpBrandEntity.brandName,
                    bpCategoryEntity.categoryName,
                    selectedPrice
                )
            )
            .from(bpBrandCategoryPriceSummaryEntity)
            .join(bpCategoryEntity).on(bpBrandCategoryPriceSummaryEntity.categoryId.eq(bpCategoryEntity.categoryId))
            .join(bpBrandEntity).on(bpBrandCategoryPriceSummaryEntity.brandId.eq(bpBrandEntity.brandId))
            .where(
                bpCategoryEntity.categoryName.eq(categoryName),
                priceExpression.eq(extremePrice)
            )
            .fetch()
    }
}
