package com.skillforge.bsc.bsc.b4.entity;

import com.skillforge.bsc.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        name = "final_objective_sources",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_final_objective_sources_final_source",
                columnNames = {"final_objective_id", "source_objective_id"}
        )
)
public class FinalObjectiveSource extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "final_objective_id", nullable = false)
    private FinalStrategicObjective finalObjective;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "source_objective_id", nullable = false)
    private StrategicObjective sourceObjective;
}
