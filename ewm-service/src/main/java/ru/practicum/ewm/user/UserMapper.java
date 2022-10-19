package ru.practicum.ewm.user;

import org.mapstruct.Mapper;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserDto userDto);

    UserDto toDto(User user);
}
