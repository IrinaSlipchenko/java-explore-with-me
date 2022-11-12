package ru.practicum.ewm.user.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.dto.UserShortDto;
import ru.practicum.ewm.user.model.User;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserIdFactory.class})
public interface UserMapper {
    User toUser(UserDto userDto);

    @Mapping(target = "friendsId", source = "friends")
    UserDto toDto(User user);

    List<UserDto> toDto(List<User> users);

    List<UserShortDto> toShortDto(List<User> users);

}
