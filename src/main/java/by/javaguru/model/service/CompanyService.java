package by.javaguru.model.service;

import by.javaguru.model.dto.CompanyCreateDto;
import by.javaguru.model.dto.CompanyReadDto;
import by.javaguru.model.entity.Company;
import by.javaguru.model.mapper.CompanyMapper;
import by.javaguru.model.repository.CompanyRepository;
import by.javaguru.listener.entity.AccessType;
import by.javaguru.listener.entity.EntityEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public CompanyService(CompanyRepository companyRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.companyRepository = companyRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public Optional<CompanyReadDto> findById(Integer id) {
        return companyRepository.findById(id)
                .map(entity -> {
                    applicationEventPublisher.publishEvent(new EntityEvent(entity, AccessType.READ));
                    return new CompanyReadDto(entity.getId(), entity.getName());
                });
    }

   public List<CompanyReadDto> findAll() {
        return companyRepository.findAll().stream()
                .map(entity -> {
                    applicationEventPublisher.publishEvent(new EntityEvent(entity, AccessType.READ));
                    return new CompanyReadDto(entity.getId(), entity.getName());
                })
                .collect(Collectors.toList());
    }

    public void delete(Integer id) {
        Optional<Company> entity = companyRepository.findById(id);
        boolean isDeleted = companyRepository.delete(id);
        if (isDeleted) applicationEventPublisher.publishEvent(new EntityEvent(entity, AccessType.DELETE));
    }

    public void create(CompanyCreateDto companyCreateDto) {
        var entity = new CompanyMapper().mapFromDto(companyCreateDto);
        companyRepository.save(entity);
        applicationEventPublisher.publishEvent(new EntityEvent(entity, AccessType.CREATE));
    }

    public void update(Integer id, String name) {
        Optional<Company> entity = companyRepository.findById(id);
        entity.ifPresent(object -> object.setName(name));
        if (entity.isPresent()) {
            companyRepository.update(entity.get());
            applicationEventPublisher.publishEvent(new EntityEvent(entity, AccessType.UPDATE));
        }
    }
}
