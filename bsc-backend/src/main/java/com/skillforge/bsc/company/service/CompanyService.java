package com.skillforge.bsc.company.service;

import com.skillforge.bsc.common.enums.CompanyStatus;
import com.skillforge.bsc.common.exception.BusinessException;
import com.skillforge.bsc.common.exception.ErrorCode;
import com.skillforge.bsc.company.dto.request.CreateCompanyRequest;
import com.skillforge.bsc.company.dto.request.UpdateCompanyRequest;
import com.skillforge.bsc.company.dto.response.CompanyResponse;
import com.skillforge.bsc.company.entity.Company;
import com.skillforge.bsc.company.mapper.CompanyMapper;
import com.skillforge.bsc.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Transactional
    public CompanyResponse create(CreateCompanyRequest request) {
        Company company = new Company();
        company.setName(normalizeRequired(request.getName()));
        company.setTaxCode(normalize(request.getTaxCode()));
        company.setIndustry(normalize(request.getIndustry()));
        company.setSize(normalize(request.getSize()));
        company.setStatus(CompanyStatus.ACTIVE);

        return companyMapper.toResponse(companyRepository.save(company));
    }

    @Transactional(readOnly = true)
    public CompanyResponse getById(UUID companyId) {
        return companyMapper.toResponse(getCompany(companyId));
    }

    @Transactional
    public CompanyResponse update(UUID companyId, UpdateCompanyRequest request) {
        Company company = getCompany(companyId);
        company.setName(normalizeRequired(request.getName()));
        company.setTaxCode(normalize(request.getTaxCode()));
        company.setIndustry(normalize(request.getIndustry()));
        company.setSize(normalize(request.getSize()));

        return companyMapper.toResponse(companyRepository.save(company));
    }

    @Transactional(readOnly = true)
    public Company getCompany(UUID companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_NOT_FOUND));
    }

    private String normalizeRequired(String value) {
        String normalized = normalize(value);
        if (normalized == null) {
            throw new BusinessException(ErrorCode.VALIDATION_ERROR, "name is required");
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
