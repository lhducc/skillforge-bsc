package com.skillforge.bsc.dashboard.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class DashboardTaskSummary {
    private long total;
    private long validTotal;
    private long todo;
    private long inProgress;
    private long review;
    private long done;
    private long blocked;
    private long cancelled;
    private long overdue;
    private BigDecimal workProgressPercent;
}
