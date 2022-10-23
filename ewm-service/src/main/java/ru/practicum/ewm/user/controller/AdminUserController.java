package ru.practicum.ewm.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.service.UserMapper;
import ru.practicum.ewm.user.service.UserServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserServiceImpl userService;
    private final UserMapper userMapper;

    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {
        User user = userMapper.toUser(userDto);
        return userMapper.toDto(userService.create(user));
    }

    @GetMapping
    public List<UserDto> getUsers(@RequestParam(required = false) List<Long> ids,
                                  @RequestParam(defaultValue = "0") Integer from,
                                  @RequestParam(defaultValue = "10") Integer size) {

        return userMapper.toDto(userService.getUsers(ids, from, size));
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {

        userService.deleteUser(userId);

    }


}
