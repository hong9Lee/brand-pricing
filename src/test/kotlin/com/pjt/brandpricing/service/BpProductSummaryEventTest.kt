package com.pjt.brandpricing.service

import com.pjt.brandpricing.application.domain.BpBrandCategoryPriceSummary
import com.pjt.brandpricing.application.domain.BpPrice
import com.pjt.brandpricing.application.domain.data.`in`.CreateProductCommand
import com.pjt.brandpricing.application.domain.event.BpProductSummaryEvent
import com.pjt.brandpricing.application.domain.event.handler.BpProductSummaryEventHandler
import com.pjt.brandpricing.application.domain.exception.ApplicationException
import com.pjt.brandpricing.application.domain.exception.ErrorCode
import com.pjt.brandpricing.application.service.command.BpProductSummaryService
import com.pjt.brandpricing.application.service.command.BpProductService
import com.pjt.brandpricing.fake.FakeBpBrandCategoryPriceSummaryCommandPort
import com.pjt.brandpricing.fake.FakeBpProductCommandPort
import com.pjt.brandpricing.fake.FakeBpProductMappingPort
import com.pjt.brandpricing.fake.FakeBpProductQueryPort
import com.pjt.brandpricing.fake.StubApplicationEventPublisher
import com.pjt.brandpricing.support.EntityId
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class BpProductSummaryEventTest {

    private val productCommandPort = FakeBpProductCommandPort()
    private val productMappingPort = FakeBpProductMappingPort()
    private val brandCategoryPriceSummaryCommandPort = FakeBpBrandCategoryPriceSummaryCommandPort()
    private val eventPublisher = StubApplicationEventPublisher()
    private val bpProductService = BpProductService(productCommandPort, productMappingPort, eventPublisher)
    private val productQueryPort = FakeBpProductQueryPort(productCommandPort, productMappingPort)
    private val summaryService = BpProductSummaryService(
        productQueryPort,
        brandCategoryPriceSummaryCommandPort
    )

    companion object {
        private const val PRODUCT_NAME = "testProductName"
    }

    @BeforeEach
    fun setup() {
        val handler = BpProductSummaryEventHandler(productMappingPort, summaryService, eventPublisher)
        eventPublisher.addListener(handler)
    }

    @Test
    @DisplayName("상품 생성 시 통계 처리 이벤트를 발행할 수 있다.")
    fun createProductEvent() {
        // when
        bpProductService.create(createProductCommand())

        // then
        eventPublisher.verifyEventPublished(BpProductSummaryEvent::class.java)
    }

    @Test
    @DisplayName("상품 생성 시 통계 데이터를 생성할 수 있다.")
    fun createProductAndGenerateSummary() {
        // when
        val result = bpProductService.create(createProductCommand())
        triggerHandler(result.productId)

        // then
        val summary = getSummary(commandBrandId, commandCategoryId)
        assertThat(summary.minPrice.value).isEqualTo(BigDecimal(10000))
        assertThat(summary.minPriceProductIds).containsExactly(result.productId)
        assertThat(summary.maxPriceProductIds).containsExactly(result.productId)
    }

    @Test
    @DisplayName("상품 생성 시 기존 통계 데이터보다 더 낮은 가격일 경우 최소값과 상품ID를 업데이트 할 수 있다.")
    fun updateSummaryWhenMinPriceChanged() {
        // given
        saveExistingSummary(BigDecimal(20000), BigDecimal(30000))

        // when
        val result = bpProductService.create(createProductCommand(price = 10000))
        triggerHandler(result.productId)

        // then
        val summary = getSummary(commandBrandId, commandCategoryId)
        assertThat(summary.minPrice.value).isEqualTo(BigDecimal(10000))
        assertThat(summary.minPriceProductIds).containsExactly(result.productId)
    }

    @Test
    @DisplayName("상품 생성 시 기존 통계 데이터보다 더 높은 가격일 경우 최대값과 상품ID를 업데이트 할 수 있다.")
    fun updateSummaryWhenMaxPriceChanged() {
        // given
        saveExistingSummary(BigDecimal(10000), BigDecimal(30000))

        // when
        val result = bpProductService.create(createProductCommand(price = 50000))
        triggerHandler(result.productId)

        // then
        val summary = getSummary(commandBrandId, commandCategoryId)
        assertThat(summary.maxPrice.value).isEqualTo(BigDecimal(50000))
        assertThat(summary.maxPriceProductIds).containsExactly(result.productId)
    }


    private val commandBrandId = EntityId()
    private val commandCategoryId = EntityId()

    private fun createProductCommand(
        productName: String = PRODUCT_NAME,
        price: Long = 10000L,
    ) = CreateProductCommand(
        productName = productName,
        price = BpPrice(price),
        brandId = commandBrandId,
        categoryId = commandCategoryId
    )

    private fun triggerHandler(productId: EntityId) {
        val handler = BpProductSummaryEventHandler(productMappingPort, summaryService, eventPublisher)
        handler.createProductSummaryHandle(BpProductSummaryEvent(productId))
    }

    private fun saveExistingSummary(min: BigDecimal, max: BigDecimal) {
        val summary = BpBrandCategoryPriceSummary(
            brandId = commandBrandId,
            categoryId = commandCategoryId,
            minPrice = BpPrice(min),
            minPriceProductIds = listOf(EntityId()),
            maxPrice = BpPrice(max),
            maxPriceProductIds = listOf(EntityId())
        )
        brandCategoryPriceSummaryCommandPort.save(summary)
    }

    private fun getSummary(brandId: EntityId, categoryId: EntityId): BpBrandCategoryPriceSummary {
        return brandCategoryPriceSummaryCommandPort.getByBrandIdAndCategoryId(brandId, categoryId)
            ?: throw ApplicationException.ofNotFound(ErrorCode.NOT_FOUND_SUMMARY)
    }
}

