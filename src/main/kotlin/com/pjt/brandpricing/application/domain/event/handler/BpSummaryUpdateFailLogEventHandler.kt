package com.pjt.brandpricing.application.domain.event.handler

import com.pjt.brandpricing.application.domain.BpSummaryUpdateFailLog
import com.pjt.brandpricing.application.domain.data.port.command.BpSummaryUpdateFailLogPort
import com.pjt.brandpricing.application.domain.event.BpSummaryUpdateFailLogEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Component
class BpSummaryUpdateFailLogEventHandler(
    private val bpSummaryUpdateFailLogPort: BpSummaryUpdateFailLogPort,
) {

    @EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun summaryUpdateFailHandle(event: BpSummaryUpdateFailLogEvent) {
        bpSummaryUpdateFailLogPort.save(
            BpSummaryUpdateFailLog.of(
                brandId = event.brandId,
                categoryId = event.categoryId,
                productId = event.productId,
                errorMessage = event.errorMessage
            )
        )
    }
}

