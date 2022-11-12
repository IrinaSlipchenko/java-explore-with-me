package ru.practicum.ewm.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.OffsetLimitPageable;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.request.dto.Status;
import ru.practicum.ewm.request.model.ParticipationRequest;
import ru.practicum.ewm.request.repository.RequestRepository;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;


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
        Pageable pageable = OffsetLimitPageable.of(0, 10);

        if (eventRepository.findAllByInitiatorId(userId, pageable).isEmpty()
                && requestRepository.findAllByRequester(user).isEmpty()) {
            userRepository.delete(user);
        } else {
            throw new ConflictException("user can not be deleted", "");
        }
    }

    @Override
    public User getById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("user not found"));
    }

    @Override
    public void friendAdd(Long userId, Long friendId) {
        User user = getById(userId);
        User friend = getById(friendId);
        user.getFriends().add(friend);
        userRepository.save(user);
        log.info("friend add, {}", user);
    }

    @Override
    public void friendDelete(Long userId, Long friendId) {
        User user = getById(userId);
        User friend = getById(friendId);
        user.getFriends().remove(friend);
        userRepository.save(user);
        log.info("friend dell, {}", user);
    }

    @Override
    public List<User> allMyFriends(Long userId) {
        User user = getById(userId);
        return new ArrayList<>(user.getFriends());
    }

//    @Override
//    public List<User> commonFriends(Long userId, Long otherId) {
//        return null;
//    }

    @Override
    public List<Event> eventsFriends(Long userId) {
        List<User> myFriends = allMyFriends(userId);

        return myFriends.stream()
                .map(requestRepository::findAllByRequester)
                .flatMap(Collection::stream)
                .filter(request -> request.getStatus().equals(Status.CONFIRMED))
                .map(ParticipationRequest::getEvent)
                .distinct()
                .collect(Collectors.toList());
    }

    private User saveOrElseThrow(User user) {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("User email already in exists", "email = " + user.getEmail());
        }
    }
}
