package com.skillforge.bsc.auth.repository;

import com.skillforge.bsc.auth.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.Optional;
import java.util.UUID;

public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {

    @EntityGraph(attributePaths = {"employee", "employee.company", "employee.department"})
    Optional<UserAccount> findByEmailIgnoreCase(String email);

    @EntityGraph(attributePaths = {"employee", "employee.company", "employee.department"})
    Optional<UserAccount> findWithEmployeeById(UUID id);

    Optional<UserAccount> findByEmployeeId(UUID employeeId);

    boolean existsByEmailIgnoreCase(String email);
}
