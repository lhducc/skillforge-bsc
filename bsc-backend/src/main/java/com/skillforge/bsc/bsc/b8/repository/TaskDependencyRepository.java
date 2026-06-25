package com.skillforge.bsc.bsc.b8.repository;

import com.skillforge.bsc.bsc.b8.entity.TaskDependency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface TaskDependencyRepository extends JpaRepository<TaskDependency, UUID> {

    List<TaskDependency> findByTargetTask_IdIn(Collection<UUID> targetTaskIds);

    boolean existsBySourceTask_IdAndTargetTask_Id(UUID sourceTaskId, UUID targetTaskId);
}
