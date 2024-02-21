package by.javaguru.service;

import by.javaguru.model.User;
import by.javaguru.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findUserById(Integer id) {
        return userRepository.findUserById(id);
    }
}

