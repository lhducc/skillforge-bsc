package com.skillforge.bsc.bsc.b5.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class CompanyFishboneResponse {

    private UUID bscStrategyId;
    private UUID companyId;
    private String companyName;
    private List<FishboneObjectiveResponse> objectives;
}
