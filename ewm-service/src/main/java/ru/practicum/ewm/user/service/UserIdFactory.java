package ru.practicum.ewm.user.service;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.user.model.User;

@Service
public class UserIdFactory {

    Long getId(User user) {
        return user.getId();
    }
}
