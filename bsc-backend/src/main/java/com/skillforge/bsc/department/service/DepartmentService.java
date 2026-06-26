package com.skillforge.bsc.department.service;

import com.skillforge.bsc.common.enums.DepartmentStatus;
import com.skillforge.bsc.common.exception.BusinessException;
import com.skillforge.bsc.common.exception.ErrorCode;
import com.skillforge.bsc.company.entity.Company;
import com.skillforge.bsc.company.service.CompanyService;
import com.skillforge.bsc.department.dto.request.CreateDepartmentRequest;
import com.skillforge.bsc.department.dto.request.UpdateDepartmentRequest;
import com.skillforge.bsc.department.dto.response.DepartmentResponse;
import com.skillforge.bsc.department.entity.Department;
import com.skillforge.bsc.department.mapper.DepartmentMapper;
import com.skillforge.bsc.department.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final CompanyService companyService;
    private final DepartmentMapper departmentMapper;

    @Transactional
    public DepartmentResponse create(UUID companyId, CreateDepartmentRequest request) {
        Company company = companyService.getCompany(companyId);
        String code = normalizeRequired(request.getCode(), "code");
        ensureCodeAvailable(companyId, code, null);

        Department department = new Department();
        department.setCompany(company);
        department.setName(normalizeRequired(request.getName(), "name"));
        department.setCode(code);
        department.setColor(normalize(request.getColor()));
        department.setDescription(normalize(request.getDescription()));
        department.setStatus(DepartmentStatus.ACTIVE);

        return departmentMapper.toResponse(departmentRepository.save(department));
    }

    @Transactional(readOnly = true)
    public List<DepartmentResponse> listByCompany(UUID companyId) {
        companyService.getCompany(companyId);
        return departmentRepository.findByCompanyId(companyId)
                .stream()
                .filter(d -> d.getStatus() == DepartmentStatus.ACTIVE)
                .map(departmentMapper::toResponse)
                .toList();
    }

    @Transactional
    public void delete(UUID departmentId) {
        Department department = getDepartment(departmentId);
        department.setStatus(DepartmentStatus.INACTIVE);
        departmentRepository.save(department);
    }

    @Transactional
    public DepartmentResponse update(UUID departmentId, UpdateDepartmentRequest request) {
        Department department = getDepartment(departmentId);
        String code = normalizeRequired(request.getCode(), "code");
        ensureCodeAvailable(department.getCompany().getId(), code, departmentId);

        department.setName(normalizeRequired(request.getName(), "name"));
        department.setCode(code);
        department.setColor(normalize(request.getColor()));
        department.setDescription(normalize(request.getDescription()));

        return departmentMapper.toResponse(departmentRepository.save(department));
    }

    @Transactional(readOnly = true)
    public Department getDepartment(UUID departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.DEPARTMENT_NOT_FOUND));
    }

    private void ensureCodeAvailable(UUID companyId, String code, UUID currentDepartmentId) {
        departmentRepository.findByCompanyId(companyId)
                .stream()
                .filter(department -> department.getCode().equalsIgnoreCase(code))
                .filter(department -> currentDepartmentId == null || !department.getId().equals(currentDepartmentId))
                .findAny()
                .ifPresent(department -> {
                    throw new BusinessException(ErrorCode.DEPARTMENT_CODE_DUPLICATED);
                });
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
