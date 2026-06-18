package com.skillforge.bsc.department.repository;

import com.skillforge.bsc.department.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {

    List<Department> findByCompanyId(UUID companyId);

    boolean existsByCompanyIdAndCode(UUID companyId, String code);
}
