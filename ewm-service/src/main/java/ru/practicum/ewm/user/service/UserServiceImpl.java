package ru.practicum.ewm.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.OffsetLimitPageable;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    @Override
    public User create(User user) {
        return saveOrElseThrow(user);
    }

    @Override
    public List<User> getUsers(List<Long> ids, Integer from, Integer size) {
        Pageable pageable = OffsetLimitPageable.of(from, size);
        if (ids != null) {
            return userRepository.findAllByIdIn(ids, pageable);
        }
        return userRepository.findAll(pageable).getContent();
    }

    @Override
    public void deleteUser(Long userId) {
        User user = getById(userId);
        userRepository.delete(user);
    }

    @Override
    public User getById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("user not found"));
    }

    private User saveOrElseThrow(User user) {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("User email already in exists", "email = " + user.getEmail());
        }
    }
}
