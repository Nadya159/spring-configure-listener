package by.javaguru.mapper;

import by.javaguru.dto.CompanyReadDto;
import by.javaguru.model.entity.Company;

public class CompanyReadMapper {
    private CompanyReadMapper companyReadMapper;

    public CompanyReadDto mapFrom(Company object){
        return new CompanyReadDto(
                object.getId(),
                object.getName()
        );
    }
}
