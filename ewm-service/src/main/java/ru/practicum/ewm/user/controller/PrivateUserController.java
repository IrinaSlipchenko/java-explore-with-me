package ru.practicum.ewm.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventDtoOutputShort;
import ru.practicum.ewm.event.service.EventMapper;
import ru.practicum.ewm.user.dto.UserShortDto;
import ru.practicum.ewm.user.service.UserMapper;
import ru.practicum.ewm.user.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/friends")
@RequiredArgsConstructor
public class PrivateUserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final EventMapper eventMapper;

    @PutMapping("/{friendId}")
    public void friendAdd(@PathVariable Long userId, @PathVariable Long friendId) {

        userService.friendAdd(userId, friendId);
    }

    @DeleteMapping("/{friendId}")
    public void friendDelete(@PathVariable Long userId, @PathVariable Long friendId) {

        userService.friendDelete(userId, friendId);
    }

    @GetMapping
    public List<UserShortDto> allMyFriends(@PathVariable Long userId) {

        log.info("get all my friends, userId={}", userId);
        return userMapper.toShortDto(userService.allMyFriends(userId));
    }

    @GetMapping("/events")
    public List<EventDtoOutputShort> eventsFriends(@PathVariable Long userId) {

        log.info("get events my friends, userId={}", userId);
        return eventMapper.toDtoShort(userService.eventsFriends(userId));
    }
}
