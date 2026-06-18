package com.skillforge.bsc.auth.entity;

import com.skillforge.bsc.common.entity.BaseEntity;
import com.skillforge.bsc.common.enums.UserAccountStatus;
import com.skillforge.bsc.common.enums.UserRole;
import com.skillforge.bsc.user.entity.Employee;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(
        name = "user_accounts",
        uniqueConstraints = @UniqueConstraint(name = "uk_user_accounts_email", columnNames = "email")
)
public class UserAccount extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private UserAccountStatus status;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;
}
