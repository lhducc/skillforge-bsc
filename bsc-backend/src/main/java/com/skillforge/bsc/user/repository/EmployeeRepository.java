package com.skillforge.bsc.user.repository;

import com.skillforge.bsc.user.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    List<Employee> findByCompanyId(UUID companyId);

    List<Employee> findByCompanyIdAndDepartmentId(UUID companyId, UUID departmentId);

    Optional<Employee> findByEmail(String email);
}
