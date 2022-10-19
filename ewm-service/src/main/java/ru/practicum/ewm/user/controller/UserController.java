package ru.practicum.ewm.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.user.UserMapper;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.service.UserServiceImpl;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;
    private final UserMapper userMapper;

    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {
        User user = userMapper.toUser(userDto);
        return userMapper.toDto(userService.create(user));
    }


}
