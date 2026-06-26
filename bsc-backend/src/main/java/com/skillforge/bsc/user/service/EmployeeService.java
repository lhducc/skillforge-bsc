package com.skillforge.bsc.user.service;

import com.skillforge.bsc.common.enums.EmployeeStatus;
import com.skillforge.bsc.common.exception.BusinessException;
import com.skillforge.bsc.common.exception.ErrorCode;
import com.skillforge.bsc.company.entity.Company;
import com.skillforge.bsc.company.service.CompanyService;
import com.skillforge.bsc.department.entity.Department;
import com.skillforge.bsc.department.service.DepartmentService;
import com.skillforge.bsc.user.dto.request.CreateEmployeeRequest;
import com.skillforge.bsc.user.dto.request.UpdateEmployeeRequest;
import com.skillforge.bsc.user.dto.response.EmployeeResponse;
import com.skillforge.bsc.user.entity.Employee;
import com.skillforge.bsc.user.mapper.EmployeeMapper;
import com.skillforge.bsc.user.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final CompanyService companyService;
    private final DepartmentService departmentService;
    private final EmployeeMapper employeeMapper;

    @Transactional
    public EmployeeResponse create(UUID companyId, CreateEmployeeRequest request) {
        Company company = companyService.getCompany(companyId);
        Department department = getDepartmentForCompany(request.getDepartmentId(), companyId);

        Employee employee = new Employee();
        employee.setCompany(company);
        employee.setDepartment(department);
        employee.setFullName(normalizeRequired(request.getFullName(), "fullName"));
        employee.setEmail(normalizeRequired(request.getEmail(), "email"));
        employee.setPhone(normalize(request.getPhone()));
        employee.setPositionTitle(normalize(request.getPositionTitle()));
        employee.setStatus(EmployeeStatus.ACTIVE);

        return employeeMapper.toResponse(employeeRepository.save(employee));
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponse> listByCompany(UUID companyId, UUID departmentId) {
        companyService.getCompany(companyId);
        if (departmentId != null) {
            getDepartmentForCompany(departmentId, companyId);
            return employeeRepository.findByCompanyIdAndDepartmentId(companyId, departmentId)
                    .stream()
                    .map(employeeMapper::toResponse)
                    .toList();
        }

        return employeeRepository.findByCompanyId(companyId)
                .stream()
                .filter(e -> e.getStatus() == EmployeeStatus.ACTIVE)
                .map(employeeMapper::toResponse)
                .toList();
    }

    @Transactional
    public EmployeeResponse update(UUID employeeId, UpdateEmployeeRequest request) {
        Employee employee = getEmployee(employeeId);
        Department department = getDepartmentForCompany(request.getDepartmentId(), employee.getCompany().getId());

        employee.setDepartment(department);
        employee.setFullName(normalizeRequired(request.getFullName(), "fullName"));
        employee.setEmail(normalizeRequired(request.getEmail(), "email"));
        employee.setPhone(normalize(request.getPhone()));
        employee.setPositionTitle(normalize(request.getPositionTitle()));

        return employeeMapper.toResponse(employeeRepository.save(employee));
    }

    @Transactional
    public void delete(UUID employeeId) {
        Employee employee = getEmployee(employeeId);
        employee.setStatus(EmployeeStatus.INACTIVE);
        employeeRepository.save(employee);
    }

    @Transactional(readOnly = true)
    public Employee getEmployee(UUID employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.EMPLOYEE_NOT_FOUND));
    }

    private Department getDepartmentForCompany(UUID departmentId, UUID companyId) {
        Department department = departmentService.getDepartment(departmentId);
        if (!department.getCompany().getId().equals(companyId)) {
            throw new BusinessException(ErrorCode.DEPARTMENT_NOT_FOUND);
        }
        return department;
    }

    private String normalizeRequired(String value, String fieldName) {
        String normalized = normalize(value);
        if (normalized == null) {
            throw new BusinessException(ErrorCode.VALIDATION_ERROR, fieldName + " is required");
        }
        return normalized;
    }

    private String normalize(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
