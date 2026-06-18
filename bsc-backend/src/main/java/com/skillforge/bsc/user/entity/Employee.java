package com.skillforge.bsc.user.entity;

import com.skillforge.bsc.common.entity.BaseEntity;
import com.skillforge.bsc.common.enums.EmployeeStatus;
import com.skillforge.bsc.company.entity.Company;
import com.skillforge.bsc.department.entity.Department;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employee extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String email;

    @Column(length = 50)
    private String phone;

    @Column(name = "position_title")
    private String positionTitle;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private EmployeeStatus status;
}
