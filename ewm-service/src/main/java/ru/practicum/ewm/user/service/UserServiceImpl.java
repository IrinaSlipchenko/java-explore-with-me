package ru.practicum.ewm.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private final UserRepository userRepository;


    public User create(User user) {

        return userRepository.save(user);
    }
}
