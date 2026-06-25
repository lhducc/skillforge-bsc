package com.skillforge.bsc.bsc.b8.repository;

import com.skillforge.bsc.bsc.b8.entity.TaskComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskCommentRepository extends JpaRepository<TaskComment, UUID> {

    List<TaskComment> findByTask_IdOrderByCreatedAtAsc(UUID taskId);
}
