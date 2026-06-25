package com.skillforge.bsc.bsc.b6.dto.request;

import com.skillforge.bsc.common.enums.BscPerspectiveCode;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class ObjectiveWeightItemRequest {

    @NotNull
    private UUID finalStrategicObjectiveId;

    @NotNull
    private BscPerspectiveCode perspectiveCode;

    @NotNull
    @DecimalMin(value = "0.000", inclusive = false)
    @DecimalMax("100.000")
    private BigDecimal weightPercent;
}
