package by.javaguru.model.service;

import by.javaguru.listener.entity.AccessType;
import by.javaguru.listener.entity.EntityEvent;
import by.javaguru.model.dto.UserReadDto;
import by.javaguru.model.repository.UserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
                    return new UserReadDto(entity.getId(), entity.getUsername());
                });
    }
}

