package ru.practicum.ewm.user.service;

import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.model.User;

import java.util.List;

public interface UserService {
    User create(User user);

    List<User> getUsers(List<Long> ids, Integer from, Integer size);

    void deleteUser(Long userId);

    User getById(Long userId);

    void friendAdd(Long userId, Long friendId);

    void friendDelete(Long userId, Long friendId);

    List<User> allMyFriends(Long userId);

    //  List<User> commonFriends(Long userId, Long otherId);

    List<Event> eventsFriends(Long userId);
}
