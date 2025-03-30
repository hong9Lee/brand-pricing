package com.pjt.brandpricing.application.domain.data.port.command

import com.pjt.brandpricing.application.domain.BpSummaryUpdateFailLog

interface BpSummaryUpdateFailLogPort {
    fun save(bpSummaryUpdateFailLog: BpSummaryUpdateFailLog): BpSummaryUpdateFailLog
}
