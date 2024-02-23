package by.javaguru.model.mapper;

import by.javaguru.model.dto.CompanyCreateDto;
import by.javaguru.model.dto.CompanyReadDto;
import by.javaguru.model.entity.Company;

public class CompanyMapper {
    public Company mapFromDto(CompanyCreateDto companyDto) {
        return Company.builder()
                .name(companyDto.username())
                .build();
    }

    public CompanyReadDto mapToDto(Company company) {
        return new CompanyReadDto(
                company.getId(),
                company.getName()
        );
    }
}
