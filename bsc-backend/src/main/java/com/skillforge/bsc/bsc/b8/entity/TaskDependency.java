package com.skillforge.bsc.bsc.b8.entity;

import com.skillforge.bsc.common.entity.BaseEntity;
import com.skillforge.bsc.common.enums.TaskDependencyType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
        name = "task_dependencies",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_task_dependencies_source_target",
                columnNames = {"source_task_id", "target_task_id"}
        )
)
public class TaskDependency extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "source_task_id", nullable = false)
    private Task sourceTask;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "target_task_id", nullable = false)
    private Task targetTask;

    @Enumerated(EnumType.STRING)
    @Column(name = "dependency_type", nullable = false, length = 50)
    private TaskDependencyType dependencyType;
}
