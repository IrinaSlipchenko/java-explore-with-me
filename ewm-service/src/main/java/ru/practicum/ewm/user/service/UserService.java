package ru.practicum.ewm.user.service;

import ru.practicum.ewm.user.model.User;

import java.util.List;

public interface UserService {
    User create(User user);

    List<User> getUsers(List<Long> ids, Integer from, Integer size);

    void deleteUser(Long userId);

    User getById(Long userId);
}
