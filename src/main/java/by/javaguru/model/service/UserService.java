package by.javaguru.model.service;

import by.javaguru.listener.entity.AccessType;
import by.javaguru.listener.entity.EntityEvent;
import by.javaguru.model.dto.UserCreateDto;
import by.javaguru.model.dto.UserReadDto;
import by.javaguru.model.entity.User;
import by.javaguru.model.mapper.UserMapper;
import by.javaguru.model.repository.UserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public UserService(UserRepository userRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.userRepository = userRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public Optional<UserReadDto> findById(Integer id) {
        return userRepository.findById(id)
                .map(entity -> {
                    applicationEventPublisher.publishEvent(new EntityEvent(entity, AccessType.READ));
                    return new UserReadDto(entity.getId(), entity.getUsername(), entity.getCompanyId());
                });
    }

    public List<UserReadDto> findAll() {
        return userRepository.findAll().stream()
                .map(entity -> {
                    applicationEventPublisher.publishEvent(new EntityEvent(entity, AccessType.READ));
                    return new UserReadDto(entity.getId(), entity.getUsername(), entity.getCompanyId());
                })
                .collect(Collectors.toList());
    }

    public void delete(Integer id) {
        Optional<User> entity = userRepository.findById(id);
        boolean isDeleted = userRepository.delete(id);
        if (isDeleted) applicationEventPublisher.publishEvent(new EntityEvent(entity, AccessType.DELETE));
    }

    public void create(UserCreateDto userCreateDto) {
        var entity = new UserMapper().mapFromDto(userCreateDto);
        userRepository.save(entity);
        applicationEventPublisher.publishEvent(new EntityEvent(entity, AccessType.CREATE));
    }

    public void update(Integer id, String name) {
        Optional<User> entity = userRepository.findById(id);
        entity.ifPresent(object -> object.setUsername(name));
        if (entity.isPresent()) {
            userRepository.update(entity.get());
            applicationEventPublisher.publishEvent(new EntityEvent(entity, AccessType.UPDATE));
        }
    }

}

