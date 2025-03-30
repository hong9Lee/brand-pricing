package com.pjt.brandpricing.application.domain.event.handler

import com.pjt.brandpricing.application.domain.data.port.command.BpProductMappingPort
import com.pjt.brandpricing.application.domain.event.BpProductSummaryEvent
import com.pjt.brandpricing.application.domain.event.BpSummaryUpdateFailLogEvent
import com.pjt.brandpricing.application.service.command.BpProductSummaryService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class BpProductSummaryEventHandler(
    private val bpProductMappingPort: BpProductMappingPort,
    private val bpProductSummaryService: BpProductSummaryService,
    private val applicationEventPublisher: ApplicationEventPublisher
) {
    val log = KotlinLogging.logger {}

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun createProductSummaryHandle(event: BpProductSummaryEvent) {
        log.info { "createProductSummaryHandle event: $event" }
        val productMapping = bpProductMappingPort.findByProductId(event.productId)

        try {
            bpProductSummaryService.recalculateSummary(
                brandId = productMapping.brandId,
                categoryId = productMapping.categoryId
            )
        } catch (e: Exception) {
            log.error { "통계 데이터 갱신 실패 error: ${e.message}" }
            applicationEventPublisher.publishEvent(
                BpSummaryUpdateFailLogEvent(
                    productId = productMapping.productId,
                    brandId = productMapping.brandId,
                    categoryId = productMapping.categoryId,
                    errorMessage = e.message ?: null
                )
            )
        }
    }
}
