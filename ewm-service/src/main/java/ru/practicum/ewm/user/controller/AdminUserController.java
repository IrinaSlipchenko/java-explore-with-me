package ru.practicum.ewm.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.service.UserMapper;
import ru.practicum.ewm.user.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {
        log.info("Creating user {}", userDto);
        User user = userMapper.toUser(userDto);
        return userMapper.toDto(userService.create(user));
    }

    @GetMapping
    public List<UserDto> getUsers(@RequestParam(required = false) List<Long> ids,
                                  @RequestParam(defaultValue = "0") Integer from,
                                  @RequestParam(defaultValue = "10") Integer size) {

        log.info("Get users all, list ids={}, from={}, size={}", ids, from, size);
        return userMapper.toDto(userService.getUsers(ids, from, size));
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {

        log.info("Delete User, Id={}", userId);
        userService.deleteUser(userId);
    }
}
