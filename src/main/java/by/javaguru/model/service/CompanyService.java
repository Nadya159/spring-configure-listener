package by.javaguru.service;

import by.javaguru.model.Company;
import by.javaguru.repository.CompanyRepository;
import by.javaguru.listener.entity.AccessType;
import by.javaguru.listener.entity.EntityEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public CompanyService(CompanyRepository companyRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.companyRepository = companyRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public Optional<Company> findCompanyById(Integer id) {
        return companyRepository.findCompanyById(id)
                .map(entity -> {
                    applicationEventPublisher.publishEvent(new EntityEvent(entity, AccessType.READ));
                    return new Company(entity.id());
                });
    }
}
