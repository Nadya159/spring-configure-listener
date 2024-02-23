package by.javaguru.model.mapper;

import by.javaguru.model.dto.UserCreateDto;
import by.javaguru.model.entity.User;

public class UserMapper {

    public User mapFromDto(UserCreateDto userDto) {
        return User.builder()
                .username(userDto.username())
                .build();
    }
}
