package com.pjt.brandpricing.infra.persistence.adapter.command

import com.pjt.brandpricing.application.domain.BpSummaryUpdateFailLog
import com.pjt.brandpricing.application.domain.data.port.command.BpSummaryUpdateFailLogPort
import com.pjt.brandpricing.infra.persistence.entity.BpSummaryUpdateFailLogEntity
import com.pjt.brandpricing.infra.persistence.repository.command.BpSummaryUpdateFailLogRepository
import org.springframework.stereotype.Component

@Component
class BpSummaryUpdateFailLogCommandAdapter(
    private val bpSummaryUpdateFailLogRepository: BpSummaryUpdateFailLogRepository,
) : BpSummaryUpdateFailLogPort {

    override fun save(bpSummaryUpdateFailLog: BpSummaryUpdateFailLog): BpSummaryUpdateFailLog {
        return bpSummaryUpdateFailLogRepository.save(BpSummaryUpdateFailLogEntity.of(bpSummaryUpdateFailLog)).toDomain()
    }
}
